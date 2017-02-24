#!/usr/bin/env python
__author__ = 'bkodeswaran'

import traceback
from random import uniform
import sys
import uuid

from bottle import route, run, request, response
import cherrypy
from cherrypy import TimeoutError
from sympy import *
from sympy.core.relational import Relational
from sympy.core.sympify import SympifyError
from sympy.parsing.sympy_parser import parse_expr

import logstasher


__python_library_version__ = '3.0.56'
__max_expr_len__ = 200

a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z = symbols('a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z')
_a1, _a2, _a3, _a4, _a5, _a6, _a7, _a8, _a9, _a10, _a11, _a12, _a13, _a14, _a15, _a16, _a17, _a18, _a19, _a20, _a21, _a22, _a23, _a24, _a25, _a26, _a27, _a28, _a29, _a30, _a31, _a32, _a33, _a34, _a35, _a36, _a37, _a38, _a39, _a40 = symbols('_a1, _a2, _a3, _a4, _a5, _a6, _a7, _a8, _a9, _a10, _a11, _a12, _a13, _a14, _a15, _a16, _a17, _a18, _a19, _a20, _a21, _a22, _a23, _a24, _a25, _a26, _a27, _a28, _a29, _a30, _a31, _a32, _a33, _a34, _a35, _a36, _a37, _a38, _a39, _a40')
_m1, _m2, _m3, _m4, _m5, _m6, _m7, _m8, _m9, _m10, _m11, _m12, _m13, _m14, _m15, _m16, _m17, _m18, _m19, _m20, _m21, _m22, _m23, _m24, _m25, _m26, _m27, _m28, _m29, _m30, _m31, _m32, _m33, _m34, _m35, _m36, _m37, _m38, _m39, _m40 = symbols('_m1, _m2, _m3, _m4, _m5, _m6, _m7, _m8, _m9, _m10, _m11, _m12, _m13, _m14, _m15, _m16, _m17, _m18, _m19, _m20, _m21, _m22, _m23, _m24, _m25, _m26, _m27, _m28, _m29, _m30, _m31, _m32, _m33, _m34, _m35, _m36, _m37, _m38, _m39, _m40')

logger = logstasher.getLogger()

# all items in data_dict will be added to the log info as fields.
# be careful not to use any reserved words as keys.
def logevent(event_name, data_dict={}):
    # add extra fields to logstash message
    extra = {'data_%s' % key: value for (key, value) in data_dict.iteritems()}
    extra['marker']='metric'
    logger.info('%s event' % event_name or 'Unknown', extra=extra)

def nthroot(b, e, evaluate=True):
    return Pow(b, 1.0 / e, evaluate)

#force translation for upper case reserved symbols to lower case
#do not translate E, I and O which stand for ln base, imaginary unit and order
#because they can potentially be used in rubrics with this meaning
evaluateOptions = {'evaluate':'evaluate','C':c,'N':n,'Q':q,'S':s,'nthroot': nthroot}

def isEquivalent(response, rubric, allowChangeOfVariable=False, allowSimplify=True, trigIdentities=False, logIdentities=False, forceAssumptions=False):
    try:
        response_expr = sympify(response, evaluateOptions)
        rubric_expr = sympify(rubric, evaluateOptions)

        #sympy forces simplification of arithmetic (in)equailities into True or
        #False
        if isinstance(response_expr, bool) or isinstance(rubric_expr, bool):
            #manually extract lhs and rhs from rubric and response
            response_ineq = isInequality(response)
            rubric_ineq = isInequality(rubric)
            if (isinstance(response_expr, bool) and response_ineq[0] > -1 or isinstance(rubric_expr, bool) and rubric_ineq[0] > -1):
                if (not response_ineq[0] > -1 or not rubric_ineq[0] > -1 or response_ineq[0] != rubric_ineq[0]):
                    return False
                if (response_ineq[0] == 0 or response_ineq[0] == 1):
                    #allow commutation
                    return isEquivalent(response_ineq[1], rubric_ineq[1], False, allowSimplify, trigIdentities, logIdentities, forceAssumptions) and \
                        isEquivalent(response_ineq[2], rubric_ineq[2], False, allowSimplify, trigIdentities, logIdentities, forceAssumptions) or \
                        isEquivalent(response_ineq[1], rubric_ineq[2], False, allowSimplify, trigIdentities, logIdentities, forceAssumptions) and \
                        isEquivalent(response_ineq[2], rubric_ineq[1], False, allowSimplify, trigIdentities, logIdentities, forceAssumptions)

                return isEquivalent(response_ineq[1], rubric_ineq[1], False, allowSimplify, trigIdentities, logIdentities, forceAssumptions) and \
                    isEquivalent(response_ineq[2], rubric_ineq[2], False, allowSimplify, trigIdentities, logIdentities, forceAssumptions)

            if (isinstance(response_expr, bool) and isinstance(rubric_expr, bool)):
                return (response_expr == rubric_expr)
            else:
                return False

        elif (isinstance(response_expr, Relational) or isinstance(rubric_expr, Relational)):
            if (isinstance(response_expr, Relational) and isinstance(rubric_expr, Relational)) and response_expr.rel_op == rubric_expr.rel_op:
                if allowSimplify:
                    try:
                        factorlr = findFactor(response_expr.rhs - response_expr.lhs, rubric_expr.rhs - rubric_expr.lhs)
                        return (factorlr > 0 or ((response_expr.rel_op == '==' or response_expr.rel_op == '!=') and factorlr != None))
                    except TypeError as e1:
                        #handling TypeError: bad operand type for unary -:
                        #'FunctionClass'
                        if str(e1).startswith("bad operand type for unary -"):
                            return False
                        raise e1
                return (isEquivalentExpressions(response_expr.rhs, rubric_expr.rhs, allowChangeOfVariable, allowSimplify, trigIdentities, logIdentities, forceAssumptions) and isEquivalentExpressions(response_expr.lhs, rubric_expr.lhs, allowChangeOfVariable, allowSimplify, trigIdentities, logIdentities, forceAssumptions) or \
                        (response_expr.rel_op == '==' or response_expr.rel_op == '!=') and isEquivalentExpressions(response_expr.lhs, rubric_expr.rhs, allowChangeOfVariable, allowSimplify, trigIdentities, logIdentities, forceAssumptions) and isEquivalentExpressions(response_expr.rhs, rubric_expr.lhs, allowChangeOfVariable, allowSimplify, trigIdentities, logIdentities, forceAssumptions))
            else:
                return False

        elif (isinstance(rubric_expr, list) or isinstance(response_expr, list)):
            if (isinstance(rubric_expr, list) and isinstance(response_expr, list)):
                return (response_expr == rubric_expr)
            else:
                return False
        elif (isinstance(rubric_expr, tuple) or isinstance(response_expr, tuple)):
            if (isinstance(rubric_expr, tuple) and isinstance(response_expr, tuple)):
                return (response_expr == rubric_expr)
            else:
                return False
        elif response_expr == S.Infinity or rubric_expr == S.Infinity:
            return (response_expr == S.Infinity and rubric_expr == S.Infinity)
        return isEquivalentExpressions(response_expr, rubric_expr, allowChangeOfVariable, allowSimplify, trigIdentities, logIdentities, forceAssumptions)
    except ValueError as e1:
        #handling ValueError: cannot convert 0 to int
        if str(e1) == 'cannot convert 0 to int' or str(e1).startswith('Cannot take to exponent') or str(e1) == 'Number too big':
            return False
        raise e1
    except AttributeError as e2:
        #handling AttributeError: 'bool' object has no attribute
        #'is_commutative'
        if str(e2).find('object has no attribute') >= 0:
            return False
        raise e2
    except OverflowError:
        #handling OverflowError: Value was either too large or too small for an
        #Int32
        return False
    except TypeError as e4:
        if str(e4).startswith('unbound method as_numer_denom() must be called with'):
            return False
        # there are other kinds of exceptions for ill formed responses like
        # sin=v that get caught by the below if clause
        if str(e4).startswith('unbound method'):
            return False
        if str(e4).startswith('unhashable type'):
            return False
        raise e4
    except CoercionFailed as e5:
        if str(e5).startswith('expected `Rational` object, got'):
            return False
        raise e5

def isEquivalentExpressions(response, rubric, allowChangeOfVariable=False, allowSimplify=True, trigIdentities=False, logIdentities=False, forceAssumptions=False):
    # check if they are simply equal.  this takes care of .3333 == .3333
    if response == rubric:
            return True

    if not allowChangeOfVariable:
        if isinstance(response, bool):
            return (type(response) == type(rubric) and response == rubric)
        elif trigIdentities:
            return simplify(expand(nsimplify(response - rubric, rational=True), trig=True)) == 0
        elif logIdentities and forceAssumptions:
            return simplify(expand(nsimplify(response - rubric, rational=True), log=True, force=True)) == 0
        elif logIdentities:
            return simplify(expand(nsimplify(response - rubric, rational=True), log=True)) == 0
        elif allowSimplify:
            return simplify(nsimplify(response - rubric, rational=True)) == 0
        else:
            return response == rubric
    if len(response.free_symbols) == 0:
        return isEquivalent(response, rubric)
    if len(response.free_symbols) > 1:
        raise Exception("Don't know how to test change of variable equivalence of 2 expressions if they have more than 1 variable. Yet")
    if len(response.free_symbols) != len(rubric.free_symbols):
        return False
    return isEquivalent(response.subs(response.free_symbols.pop(),rubric.free_symbols.pop()), rubric)

# Check if exp1 = f*exp2 for f=1 or -1
def findFactor(exp1, exp2):
    set1 = exp1.atoms(Function, Symbol)
    set2 = exp2.atoms(Function, Symbol)
    if (len(set1.symmetric_difference(set2)) > 0):
        return None
    else:
        set1w, set1r = determineBounds(exp1, set1)
        set2w, set2r = determineBounds(exp2, set2)

        subs_int = 200
        subs_int_reduced = 4
        subs_iter = 20
        subs_error = 1e-8
        factor = None
        for i in range(0, subs_iter):
            subs_map = {}
            for param in set1r:
                subs_map[param] = uniform(-subs_int_reduced / 2, subs_int_reduced / 2)
            for param in set1w:
                if param in set2w:
                    subs_map[param] = uniform(-subs_int / 2, subs_int / 2)
                else:
                    subs_map[param] = uniform(-subs_int_reduced / 2, subs_int_reduced / 2)

            try:
                subsexp1 = exp1.subs(subs_map)
            except (SympifyError, TypeError, AttributeError, ZeroDivisionError, ValueError):
                return None

            subsexp2 = exp2.subs(subs_map)

            if subsexp2 in [0, False, True]:
                if subsexp1 == subsexp2:
                    updated_factor = 1
                else:
                    updated_factor = -1
            else:
                updated_factor = subsexp1 / subsexp2

            if factor and abs(factor - updated_factor) > subs_error:
                return None
            factor = updated_factor

        return factor

    return None

def determineBounds(expr, symbset):
    setw = symbset
    setr = set([])
    try:
        for arg in preorder_traversal(expr):
            if (arg.is_Pow or arg.is_Function and arg.func == exp):
                for symb in setw:
                    if symb in arg.atoms(Function, Symbol):
                        setr.add(symb)
                setw -= setr
    except:
        pass

    return setw, setr

def isInequality(exprstr):
    #trim () before looking for equality prefix
    while (len(exprstr) > 1):
        if exprstr.startswith('(') and exprstr.endswith(')'):
            exprstr = exprstr[1:-1]
        else:
            break

    if (len(exprstr) < 5 or not exprstr[0:3] in ('Eq(','Le(','Lt(','Ge(','Gt(','Ne(') or not exprstr.endswith(')')):
        return -1, None, None
    indlhs = exprstr.find(',')
    if (not indlhs > -1 or parsable(exprstr[3:indlhs]) or parsable(exprstr[indlhs + 1:-1])):
        return -1, None, None
    eqtype = exprstr[0:3]
    if (eqtype == 'Eq('):
        return 0, exprstr[3:indlhs], exprstr[indlhs + 1:-1]
    if (eqtype == 'Ne('):
        return 1, exprstr[3:indlhs], exprstr[indlhs + 1:-1]
    if (eqtype == 'Le('):
        return 2, exprstr[3:indlhs], exprstr[indlhs + 1:-1]
    if (eqtype == 'Ge('):
        return 2, exprstr[indlhs + 1:-1], exprstr[3:indlhs]
    if (eqtype == 'Lt('):
        return 3, exprstr[3:indlhs], exprstr[indlhs + 1:-1]
    if (eqtype == 'Gt('):
        return 3, exprstr[indlhs + 1:-1], exprstr[3:indlhs]

def garbageResponseFilter(response):
    if (len(response) > __max_expr_len__):
        try:
            float(response)
        except:
            raise ValueError("Expression is too long")

def parsable(response):
    try:
        garbageResponseFilter(response)
        sympify(response, evaluateOptions)
    except (SympifyError, TypeError, AttributeError, ZeroDivisionError, ValueError, MemoryError):
        return 1
    else:
        return 0

#turns a list ['a > 2 and a <= 4', 'b > 1 or b < 8', 'c != 3', 'd == 2 or d ==
#4']
#into a list [(lambda a: (a > 2 and a<=4))] for parname='a', or into [(lambda
#b: (b>1 or b<8))] for parname='b'
def parse_constraints(constraints, parname):
    ret = []

    try:
        for constraint in constraints:
            #find out if parname is mentioned in this constraint
            basicconstraint = constraint
            for allowedsymbol in ['and', 'or', '<', '<=', '>', '>=', '==', '!=']:
                basicconstraint = ' '.join(basicconstraint.split(allowedsymbol))
            if parname in basicconstraint.split():
                ret.append(parse_expr('lambda ' + parname + ': (' + constraint + ')', {'and':'and', 'or':'or', parname:parname}))
    except:
        pass

    return ret

# Matches expression to a pattern in a rubric, returns a set of numeric values
# parameters = ['a','b']; variables = ['t','x'];
def matchDouble(response, rubric, parameters, constraints, variables):
    try:
        response_expr = sympify(response,evaluateOptions)
        rubric_expr = sympify(rubric, evaluateOptions)

        pvariables = []
        for vr in variables:
            pvariables.append(Symbol(vr))

        for ind, par in enumerate(parameters):
            constraints_expr = parse_constraints(constraints, par)
            rubric_expr = rubric_expr.subs(sympify(par), Wild(par, exclude=pvariables, properties=constraints_expr))

        retset = []
        if isinstance(response_expr, list):
            return retset

        dict1 = {}
        try:
            dict1 = response_expr.match(rubric_expr)
        except:
            pass

        if dict1:
            for par in parameters:
                prfound = False
                for ky, vr in dict1.iteritems():
                    if (ky.name == par) and (vr is not None) and (vr.is_number) and (vr.is_Atom):
                        retset.append(float(vr))
                        prfound = True
                        break
                if (not prfound):
                    retset.append(nan)

        return retset
    except ValueError as e1:
        #handling ValueError: cannot convert 0 to int
        if str(e1) == 'cannot convert 0 to int' or str(e1).startswith('Cannot take to exponent') or str(e1) == 'Number too big':
            return []
        raise e1
    except AttributeError as e2:
        #handling AttributeError: 'bool' object has no attribute
        #'is_commutative'
        if str(e2).find('object has no attribute') >= 0:
            return []
        raise e2
    except OverflowError:
        #handling OverflowError: Value was either too large or too small for an
        #Int32
        return []
    except TypeError as e4:
        if str(e4).startswith('unbound method as_numer_denom() must be called with'):
            return []
         # there are other kinds of exceptions for ill formed responses like
         # sin=v that get caught by the below if clause
        if str(e4).startswith('unbound method'):
            return []
        raise e4
    except CoercionFailed as e5:
        if str(e5).startswith('expected `Rational` object, got'):
            return []
        raise e5

def bestMatch(response, rubric, var):
    m = response.match(rubric)
    if (m):
        return m

    #find factors of x
    ret = {}
    if len(var) <= 1 and rubric.is_Add and response.is_Add and len(rubric.args) == len(response.args):
        for a in rubric.args:
            t1 = a.as_independent(var[0])
            amatched = False
            for b in response.args:
                t2 = b.as_independent(var[0])
                if (t1[1] == t2[1]):
                     ret.update(t2[0].match(t1[0]))
                     amatched = True
                     break

    elif len(var) <= 1 and rubric.is_Equality and response.is_Equality:
        #making sure we match everything even if a subexpression does not
        #contain wildcards
        if rubric.lhs.atoms(Wild) == set([]):
            atmset = response.lhs.atoms(Symbol)
            for atm in atmset:
                try:
                    if (atm.name.startswith('_a')):
                        response = response.subs(atm,0)
                    elif (atm.name.startswith('_m')):
                        response = response.subs(atm,1)
                except:
                        pass
            if not isEquivalent(response.lhs, rubric.lhs):
                return ret

        if rubric.rhs.atoms(Wild) == set([]):
            atmset = response.rhs.atoms(Symbol)
            for atm in atmset:
                try:
                    if (atm.name.startswith('_a')):
                        response = response.subs(atm,0)
                    elif (atm.name.startswith('_m')):
                        response = response.subs(atm,1)
                except:
                        pass
            if not isEquivalent(response.rhs, rubric.rhs):
                return ret

        ret.update(bestMatch(response.args[0], rubric.args[0], var))
        ret.update(bestMatch(response.args[1], rubric.args[1], var))

    return ret

# Matches expression to a pattern in a rubric, returns a set of expressions
# parameters = ['a','b']; variables = ['t','x'];
def matchExpression(response, rubric, parameters, constraints, variables):
    try:
        response_expr = sympify(response,evaluateOptions)
        rubric_expr = sympify(rubric, evaluateOptions)

        pvariables = []
        for vr in variables:
            pvariables.append(sympify(vr, evaluateOptions))
            #pvariables.append(Symbol(vr))

        for ind, par in enumerate(parameters):
            constraints_expr = parse_constraints(constraints, par)
            rubric_expr = rubric_expr.subs(sympify(par), Wild(par, exclude=pvariables, properties=constraints_expr))

        retset = []
        if isinstance(response_expr, list):
            return retset

        dict1 = {}
        try:
            dict1 = bestMatch(response_expr, rubric_expr, pvariables)
        except:
            pass

        if dict1:
            for par in parameters:
                prfound = False
                for ky, vr in dict1.iteritems():
                    if (ky.name == par) and (vr is not None):
                        try:
                            atmset = vr.atoms(Symbol)
                            #replacing _ai and _mi for each of the matched
                            #subexpressions
                            #--take out this contraint--if there are more than
                            #one occurrence of _ai or _mi, match is not valid
                            for atm in atmset:
                                try:
                                    if (atm.name.startswith('_a')):
                                        vr = vr.subs(atm,0)
                                        #break
                                except:
                                    pass
                            atmset = vr.atoms(Symbol)
                            for atm in atmset:
                                try:
                                    if (atm.name.startswith('_m')):
                                        vr = vr.subs(atm,1)
                                        #break
                                except:
                                    pass
                            atmset = vr.atoms(Symbol)
                            for atm in atmset:
                                try:
                                    if (atm.name.startswith('_a') or atm.name.startswith('_m')):
                                        return [] #invalidate the match
                                except:
                                    pass
                        except:
                            pass
                        retset.append(str(vr))
                        prfound = True
                        break
                if (not prfound):
                    retset.append('')

        return retset
    except ValueError as e1:
        #handling ValueError: cannot convert 0 to int
        if str(e1) == 'cannot convert 0 to int' or str(e1).startswith('Cannot take to exponent') or str(e1) == 'Number too big':
            return []
        raise e1
    except AttributeError as e2:
        #handling AttributeError: 'bool' object has no attribute
        #'is_commutative'
        if str(e2).find('object has no attribute') >= 0:
            return []
        raise e2
    except OverflowError:
        #handling OverflowError: Value was either too large or too small for an
        #Int32
        return []
    except TypeError as e4:
        if str(e4).startswith('unbound method as_numer_denom() must be called with'):
            return []
         # there are other kinds of exceptions for ill formed responses like
         # sin=v that get caught by the below if clause
        if str(e4).startswith('unbound method'):
            return []
        raise e4
    except CoercionFailed as e5:
        if str(e5).startswith('expected `Rational` object, got'):
            return []
        raise e5

def evaluateExpression(response):
    return N(response, chop=True)

#tests to remain valid after implementation change
def unittestlib():
    print "starting unit tests"
    evaluatelib()
    eqtestlib()
    matchtestlib()
    sbactest()
    hitest()
    revisetest()
    utahtest()
    print "all passed"
    sys.stdout.flush()

def evaluatelib():
    assert evaluateExpression('sqrt(4)') == 2.0
    assert evaluateExpression('nthroot(27,3)') == 3.0

def eqtestlib():
    #numbers (integers, floats, operators +-*/,)
    assert isEquivalent('2', '1+1')
    assert not isEquivalent('2', '1')
    assert isEquivalent('2.1', '0.21e+1')
    assert isEquivalent('1/3', '2/6')
    assert not isEquivalent('1/3', '0.333')
    assert isEquivalent('2', '2.0')
    assert isEquivalent('Eq(x,2)', 'Eq(x,2.0)')
    assert isEquivalent('Eq(x,2)', 'Eq(x,1+1)')
    assert isEquivalent('Eq(2*x+4,2)', 'Eq(x+2,1)')
    #arithmetic equalities
    assert isEquivalent('Eq(1,5-4+3-2-1)','Eq(1,1)', allowSimplify = True)
    #assert not isEquivalent('Eq(1,5-4+3-2-1)','Eq(1,1)', allowSimplify =
    #False)
    assert isEquivalent('Eq(1,1)', 'Eq(1,5-4+3-2-1)', allowSimplify = True)
    #assert not isEquivalent('Eq(1,1)', 'Eq(1,5-4+3-2-1)', allowSimplify =
    #False)
    assert not isEquivalent('Eq(1,5-4+3-2-1)','True')
    assert not isEquivalent('True', 'Eq(1,5-4+3-2-1)')
    assert isEquivalent('True', 'True')
    assert isEquivalent('False', 'False')
    assert not isEquivalent('True', 'False')
    assert not isEquivalent('2','3')
    assert not isEquivalent('Eq(2,1)','Eq(3,2)')
    assert isEquivalent('Eq(2,1)','Eq(2,1)')
    assert isEquivalent('Eq(2,1)','Eq(1,2)')
    assert isEquivalent('Eq(1+1,1)','Eq(1,2)')
    assert isEquivalent('Ne(2,1)','Ne(2,1)')
    assert isEquivalent('Ne(2,1)','Ne(1,2)')
    assert isEquivalent('Le(1,2)','Le(1,2)')
    assert isEquivalent('Ge(2,1)','Le(1,2)')
    assert isEquivalent('Ge(1+1,1)','Le(1,2)')
    assert not isEquivalent('Ge(3,1)','Le(1,2)')
    assert not isEquivalent('Ge(1+1+1,1)','Le(1,2)')
    assert not isEquivalent('Le(1,2)','Lt(1,2)')
    assert isEquivalent('Lt(1,2)','Lt(1,2)')
    assert isEquivalent('Gt(2,1)','Lt(1,2)')
    assert isEquivalent('Eq(9,63/7)','Eq(63/7,9)')
    #parse correctly evaluate keyword used for disabling auto simplifications
    assert isEquivalent('Eq(x,1)','Eq(x,Add(2,-1,evaluate=True))')
    #reloaded division operator to float division
    assert isEquivalent('Eq(x,0.5)', 'Eq(x,1/2)')
    assert isEquivalent('Eq(x,0.5)', 'Eq(x,.5)')
    assert isEquivalent('0.5', '.5')
    assert isEquivalent('x**(1/2)','x**0.5')
    #multiplication expansion
    assert isEquivalent('a*(a+b)', 'a**2+a*b', allowSimplify = True)
    assert isEquivalent('a*(a+b)', 'a**2.0+a*b', allowSimplify = True)
    assert not isEquivalent('a*(a+b)', 'a**2+a*b', allowSimplify = False)
    assert isEquivalent('Eq(y,x**2-6*x+5)', 'Eq(y,(x-1)*(x-5))', allowSimplify = True)
    assert not isEquivalent('Eq(y,x**2-6*x+5)', 'Eq(y,(x-1)*(x-5))', allowSimplify = False)
    assert isEquivalent('Eq(y,(x-1)*(x-5))', 'Eq(x**2-6*x+5,y)', allowSimplify = True)
    assert not isEquivalent('Eq(y,(x-1)*(x-5))', 'Eq(x**2-6*x+5,y)', allowSimplify = False)
    assert isEquivalent('Eq(a,2*b+1)','Eq(-2*b-1,-a)')
    assert not isEquivalent('Eq(a,2*b+1)','Eq(-2*b-1,-a+1)')
    #Trig functions and identities
    assert isEquivalent('cos(t)', 'sin(t+pi/2)')
    assert isEquivalent('cos(t+pi/2)', '-sin(t)')
    assert isEquivalent('sin(2*x)','2*sin(x)*cos(x)',trigIdentities=True)
    assert isEquivalent('cos(2*x)','1-2*sin(x)**2',trigIdentities=True)
    assert isEquivalent('cos(x/0.5)','1-2*sin(x)**2',trigIdentities=True)
    assert isEquivalent('sin(x)**2+cos(x)**2', '1')
    #Log functions and identities
    assert isEquivalent('exp(2*x)','(exp(x))**2')
    assert isEquivalent('exp(ln(x))','x')
    assert isEquivalent('log(x**2)','2*log(x)',logIdentities=True,forceAssumptions=True)
    #Solve system of equations/inequalities
#    assert hasEquivalentSolutions('[Eq(2*x-3,2*x-5)]','[Eq(x,x+3)]','')
#    assert not hasEquivalentSolutions('[Eq(-5*b+1,5*b)]','[Eq(x,x+3)]','')
    assert not isEquivalent("Eq(28.80*y*3*h,86.4*y*h)", "Eq(y,9.6*h)")
    assert not isEquivalent('Eq(y,3)','Eq(y,3*x-2)')
    assert not isEquivalent('(Eq(2*x+6*y,25.50))','Eq(y,-10.8*x**2+83.08*x-9.99)')
    #Rounding error
    assert isEquivalent('Eq(x,7)','Eq(3.45*x,24.15)')
    assert not isEquivalent('Eq(x,7.1)','Eq(3.45*x,24.15)')
    #Relational equivalence
    assert isEquivalent('Eq(x,1)', 'Eq(1,x)')
    assert isEquivalent('Gt(x,1)', 'Lt(1,x)')
    assert not isEquivalent('Gt(x,1)', 'Lt(x,1)')
    assert isEquivalent('Ne(x,1)', 'Ne(1,x)')
    assert isEquivalent('Ge(x,1)', 'Le(1,x)')
    assert not isEquivalent('Ge(x,1)', 'Le(x,1)')
    assert isEquivalent('Ge(x,6.99999999999999999)', 'Le(7.0,x)')
    assert isEquivalent('Lt(225,x)', 'Gt(4*x,900)')
    assert not isEquivalent('Lt(225.1,x)', 'Gt(4*x,900)')
    assert not isEquivalent('Lt(x,225)', 'Gt(4*x,900)')
    #adding second representation for exponentials
    assert isEquivalent('Eq(y,(x)**(1/3))', 'Eq(y,(x)**(1/3))', allowSimplify = True)
    assert isEquivalent('Eq(y,nthroot(x,3))', 'Eq(y,(x)**(1/3))',allowSimplify = True)
    assert isEquivalent('Eq(y,nthroot(x,3))', 'Eq(y,nthroot(x,3))',allowSimplify = True)
    assert isEquivalent('Eq(y,nthroot(x,3))', 'Eq(y,(x)**(1/3))',allowSimplify = True)
    assert isEquivalent('Eq(y,nthroot(x,2))', 'Eq(y,sqrt(x))',allowSimplify = True)
    assert isEquivalent('((((x*y))*12+((2*x+2*y))*z))*1.25', '2.5*(6*x*y+x*z+y*z)', allowSimplify = True)

def matchtestlib():
    assert matchExpression('cos(pi*t)', 'a*cos(b*t+c)+d', ['a','b','c','d'], [], ['t']) == ['1','pi','0','0']
    #constraints check
    assert matchExpression('2*x+1', 'a*x+b', ['a','b'], [], ['x']) == ['2','1']
    assert matchExpression('2*x+1', 'a*x+b', ['a','b'], ['a>1 and a<4', 'b==2 or b==1'], ['x']) == ['2','1']
    assert matchExpression('5*x+1', 'a*x+b', ['a','b'], ['a>1 and a<4', 'b==2 or b==1'], ['x']) == []
    assert matchExpression('2*x+3', 'a*x+b', ['a','b'], ['a>1 and a<4', 'b==2 or b==1'], ['x']) == []
    assert matchExpression('5*x+3', 'a*x+b', ['a','b'], ['a>1 and a<4', 'b==2 or b==1'], ['x']) == []
    assert matchExpression('5+4*x+3*x**2+2*x**3', 'a*x**3+b*x**2+c*x+d', ['a','b','c','d'], [], ['x']) == ['2','3','4','5']
    assert matchExpression('5+4*x+3*x**2+2*x**3', 'a*x**3+b*x**2+c*x+d', ['a','b','c','d'], ['a>1','b==3 or b==4','c!=5','d>4 or d<-5'], ['x']) == ['2','3','4','5']
    assert matchExpression('5+4*x+3*x**2-2*x**3', 'a*x**3+b*x**2+c*x+d', ['a','b','c','d'], ['a>1','b==3 or b==4','c!=5','d>4 or d<-5'], ['x']) == []
    assert matchExpression('5+4*x+4*x**2+2*x**3', 'a*x**3+b*x**2+c*x+d', ['a','b','c','d'], ['a>1','b==3 or b==4','c!=5','d>4 or d<-5'], ['x']) == ['2','4','4','5']
    assert matchExpression('5+4*x+2*x**2+2*x**3', 'a*x**3+b*x**2+c*x+d', ['a','b','c','d'], ['a>1','b==3 or b==4','c!=5','d>4 or d<-5'], ['x']) == []
    assert matchExpression('5+4*x+5*x**2+2*x**3', 'a*x**3+b*x**2+c*x+d', ['a','b','c','d'], ['a>1','b==3 or b==4','c!=5','d>4 or d<-5'], ['x']) == []
    assert matchExpression('5+5*x+3*x**2+2*x**3', 'a*x**3+b*x**2+c*x+d', ['a','b','c','d'], ['a>1','b==3 or b==4','c!=5','d>4 or d<-5'], ['x']) == []
    assert matchExpression('3+4*x+3*x**2+2*x**3', 'a*x**3+b*x**2+c*x+d', ['a','b','c','d'], ['a>1','b==3 or b==4','c!=5','d>4 or d<-5'], ['x']) == []
    assert matchExpression('-6+4*x+3*x**2+2*x**3', 'a*x**3+b*x**2+c*x+d', ['a','b','c','d'], ['a>1','b==3 or b==4','c!=5','d>4 or d<-5'], ['x']) == ['2','3','4','-6']
    #check for disabled automatic simplification
    assert matchExpression('3*x**2+5*x+6', 'a*x**2+b*x+c', ['a','b','c'], [], ['x']) == ['3','5','6']
    assert matchExpression('((3+_a1)*_m1)*x**2+((5+_a2)*_m2)*x+((6+_a3)*_m3)', 'a*x**2+b*x+c', ['a','b','c'], [], ['x']) == ['3','5','6']
    assert matchExpression('Eq(((3+_a1)*_m1)*x+((5+_a2)*_m2),((6+_a3)*_m3))', 'Eq(a*x+b,c)', ['a','b','c'], [], ['x']) == ['3','5','6']
    assert (matchExpression('Eq(((3+_a1)*_m1)+((5+_a2)*_m2),((8+_a3)*_m3))', 'Eq(a+b,c)', ['a','b','c'], [], []) == ['3','5','8'] or \
            matchExpression('Eq(((3+_a1)*_m1)+((5+_a2)*_m2),((8+_a3)*_m3))', 'Eq(a+b,c)', ['a','b','c'], [], []) == ['5','3','8'])
    assert matchExpression('1+1', 'a', ['a'], [], []) == ['2']
    assert matchExpression('2', 'a', ['a'], [], []) == ['2']
    assert matchExpression('Eq((3+_a1)*_m1*(8+_a2)*_m2,(24+_a3)*_m3)','Eq(a*b,24)', ['a','b'], [], []) == ['3','8']
    #still does not work, why?
    #assert matchDouble('cos(t+pi,evaluate=False)', 'a*cos(b*t+c)+d',
    #['a','b','c','d'], [], ['t'])==[1.0, 1.0, 3.141592653589793, 0.0]
    assert matchExpression('sqrt(6)+(3)**(1/(2))*I','p+q*I',['p','q'],[],['I']) == ['6**(1/2)', '3**(1/2)']
    assert matchExpression('sqrt(6)','p+q*I',['p','q'],[],['I']) == ['6**(1/2)', '0']
    #checking second representation for exponentials
    assert matchExpression('nthroot(10,4)*x**2+nthroot(5,2)*x+nthroot(7,8)', 'a*x**2+b*x+c', ['a','b','c'], [], ['x']) == ['10**0.25', '5**0.5', '7**0.125']
    assert matchExpression('Eq(nthroot(5,2)*x+nthroot(7,8)+nthroot(10,4)*x**2,y)', 'Eq(a*x**2+b*x+c,y)', ['a','b','c'], [], ['x','y']) == ['10**0.25', '5**0.5', '7**0.125']
    assert matchExpression('nthroot(6,4)+nthroot(3,2)*I','p+q*I',['p','q'],[],['I']) == ['6**0.25', '3**0.5']
    assert matchExpression('nthroot(6,4)','p+q*I',['p','q'],[],['I']) == ['6**0.25', '0']

def matchDblTest():
    assert matchDouble('1+1', 'a+b', ['a','b'], [], []) == [0.0, 2.0]
    assert matchDouble('(2/1)', 'a/b', ['a','b'], [], []) == [0.0, 2.0]

def sbactest():
    sympify('Eq(64.80,16.20*f((15.50/1.00)))')
    assert not isEquivalent('Eq(3,s)', 'Le(3,4)')
    assert not isEquivalent('Le(3,s)', 'Le(3,4)')
    assert isEquivalent('Eq(x/2,x*(1/2))','Eq(x/2,x/2)')
    #does not pass yet
    #assert not isEquivalent('Eq(y,y)','Eq(x,x)')
    assert not isEquivalent('(26)','Eq(N*k,(4)**(k))')
    assert isEquivalent('Eq((4)**(k),N*k)','Eq(N*k,(4)**(k))')
    assert not isEquivalent('Eq(x,1)','x')
    if not parsable('(Eq(310+810+750+670+400+80+40+15,2835...0))'):
        assert not isEquivalent('(Eq(310+810+750+670+400+80+40+15,2835...0))','Eq(x,0)')
    assert not isEquivalent('(Eq(y,77.00*((Eq(x,5)))))','Eq(x,0)')
    assert isEquivalent('.0','0')
    assert isEquivalent('(box*box*box*box*box*box*box*box*box*box*box*box*box*.0)', '0')
    assert not isEquivalent('(Eq(t,P-21.98))', 'Eq(P*(t),(0.022*(t**2)+92))')
    assert isEquivalent('Eq(P*(t),(0.022*(t**2)+92))', 'Eq(P*(t),(0.022*(t**2)+92))')
    assert isEquivalent('Eq((P*(t)-92)/0.022,(t**2))', 'Eq(P*(t),(0.022*(t**2)+92))')
    assert not isEquivalent('(Eq(t,P-21.98))', 'Eq(P*(t),(0.022*(t**2)+92))')
    assert not isEquivalent('Eq(P*(t),(0.022*(t**2)+92-1))', 'Eq(P*(t),(0.022*(t**2)+92))')
    assert not isEquivalent('(91.97/0)',"Eq(P*(t),(0.022*(t**2)+92))")
    assert not isEquivalent('((30)/0)','2/3')
    if not parsable('Eq(5,2)/Eq(3,2)'):
        assert not isEquivalent('Eq(5,2)/Eq(3,2)','Eq(x,0)')
    assert parsable('(6*((69))*56)/(0.0)')
    assert parsable('(Lt(69,9*(Eq(cos(6)*atan(69,88)))))')
    #number too big would throw ValueError
    if not parsable('(4.15*((10))**((2*((20))**((2+500)))))'):
        assert not isEquivalent('(4.15*((10))**((2*((20))**((2+500)))))','15')
    #does not pass, yet
    #assert not isEquivalent('(6.48*1*(0)**(((-10))))','x')
        # precision issues
        assert isEquivalent('Eq(L,40/3.1)', 'Eq(L,12.903225806545)')
        assert isEquivalent('Eq(L,40/3.1)', 'Eq(L,12.9032258065)')
        assert not isEquivalent('Eq(L,40/3.1)', 'Eq(L,12.90322581)')
        # some sample response that used to crash the scorer
        assert not isEquivalent('sin((cos((tan((asin((acos((atan((6))*4))*4))*7))*8))*2))*5', '3', False, True, False, False, False)

def hitest():
    assert isEquivalent('(Eq(f(5)*30+2.25,152.25))','Eq(f(5),5)')
    assert isEquivalent('Eq(f(5)+1,5+1)','Eq(f(5),5)')
    assert isEquivalent('Eq(f(5)*2,5*2)','Eq(f(5),5)')
    assert not isEquivalent('Eq(f(4),5)','Eq(f(5),5)')
    assert not isEquivalent('Eq(f(5),4)','Eq(f(5),5)')
    assert not parsable('123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890')
    assert not isEquivalent('123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890','1')
    assert parsable('I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I*I')
    assert isEquivalent('(Eq(f((60))*x,60.25))','Eq(x*f((60))/60.25,1)')

def revisetest():
    assert not isEquivalent('Eq(28.80*y*3*h,86.4*y*h)','Eq(y,9.6*h)')
    assert S('1').is_positive
    assert S('1+2').is_positive
    assert S('-1+pi').is_positive
    assert S('5*(-1+pi)').is_positive
    assert (S('f(5)').is_positive is None)
    assert (S('f(5)-1').is_positive is None)
    assert (S('5*(f(5)-1)').is_positive is None)
    assert isEquivalent('Ne(x,1)','Ne(1,x)')
    assert isEquivalent('Le(x,1)','Ge(1,x)')

def utahtest():
    assert not isEquivalent('3/0','3/5')
    assert not isEquivalent('3.0/0','3/5')
    assert not parsable('3/0')
    assert not isEquivalent('3/0','3.0/5.0')
    assert not isEquivalent('3.0/5.0', '3/0')
    assert not isEquivalent('3/0-1+2','3.0/5.0')
    #assert isEquivalent('3.0/5.0', '3/5')
    #assert isEquivalent('3/5', '3.0/5.0')
    if not parsable('asin((acos((atan((tan((cos((cos((cos((-))))))))))))))'):
        assert not isEquivalent('asin((acos((atan((tan((cos((cos((cos((-))))))))))))))','x+5')
    assert not isEquivalent('Eq(cos,((f((t))*5)/t))', 'Eq(f((t)),-cos((4*pi*t))+5)')
    assert not isEquivalent('f(Eq(((-9)),-(2)**(((2)))-2))','2*x**2+5*x-7')
    assert not isEquivalent('Eq(((((((((((x*x**65748714681489)))))))))),((((289458921498446251441100458108410401840184015401450184701874017110450)/box))))', 'Eq(26*4,104)')
    assert not isEquivalent('Eq(y,((220.00)/0))', 'Eq(y,220*(.9)**(t))')
    assert not isEquivalent('Eq(f((n)),9+63+55+65+363+52+41-56/65+64+85+36-95*86/56-47+98-65*nthroot((35),(6999999965)))','Eq(f(n),5*n+8)')
    assert parsable('sin((cos((tan((tan((asin((acos((atan(Lt((,6)))))))))))))))')
    assert not parsable('0.333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333')
    assert isEquivalent('0.333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333', '1/3')
    assert not parsable('81.00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000')
    assert isEquivalent('81.00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', '81')
    assert not parsable('26.00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000')
    assert isEquivalent('26.00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', '26')
    assert not parsable('-75.13790000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000')
    assert isEquivalent('-75.13790000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', '-75.1379')
    assert not parsable('8.333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333')
    assert isEquivalent('8.333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333', '8+1/3')
    assert not isEquivalent('Eq(5.5*p*2.0,sin((6)))', 'Eq(2.75**(1/14), (1+p/100))')
    assert not isEquivalent('Eq(t+5*h+150*c,30*m)', 'Eq(t,c+m*h)')
    assert not isEquivalent('Eq(t+5*h+150*c,30*m)', 'Eq(1,(c+m*h)/t)')
    assert not isEquivalent('Eq(t+5*h+150*c,30*m)', 'Eq(t/(c+m*h),1)')
    assert not isEquivalent('Eq(t+5*h+150*c,30*m)', 'Eq((t-c)/(m*h),1)')
    assert not isEquivalent('Eq(t+5*h+150*c,30*m)', 'Eq((t-c)/h,m)')
    assert not isEquivalent('Eq(t+5*h+150*c,30*m)', 'Eq((t-c)/m,h)')
    assert not isEquivalent('Eq(t+5*h+150*c,30*m)', 'Eq((t-m*h)/c,1)')
    assert not isEquivalent('Eq(t+5*h+150*c,30*m)', 'Eq(c/(t-m*h),1)')
    assert not isEquivalent('Eq(t+5*h+150*c,30*m)', 'Eq(1/h,m/(t-c))')
    assert not isEquivalent('Eq(t+5*h+150*c,30*m)', 'Eq(1/m,h/(t-c))')
    assert not isEquivalent('Eq(2500,(1000)*((1+((((r/4))))**(((4*7))))))','Eq(2500,1000*(((1+(r/4))))**(((28))))')
    assert isEquivalent('Eq(2500,1000*(1+r/4)**(4*7))','Eq(2500*2+1,1000*(1+r/4)**(4*7)*2+1)')
    assert matchExpression('Eq(((4+_a7)*_m7)*((5+_a6)*_m6)+((20+_a5)*_m5),((40+_a8)*_m8))','(c1/d1)*x+f1',['c1','d1','f1'],[],['x']) == ['0','','True']
    assert not isEquivalent('sin','(x-5*y*I)*(x+5*y*I)')
    assert not isEquivalent('4*y*nthroot(y,((Abs(744487))))', '(3)*(y)*(0.65)')
    assert not isEquivalent('(((251.05)))**(((5466532)))', '5')
    assert not isEquivalent("40*1.05*(0)**(((n-1)))","40*(1.05)**(n-1)")
    assert not isEquivalent('Eq(g(5),-0.5*x+512)', 'Eq(g(x),-.5*x+512)')
    assert not isEquivalent("Eq(w+((w+1)),(Eq(4.5+6,t)))","Eq(t,4.5*w+6)")
    assert not isEquivalent('Eq(g(5),-0.5*x+512)', 'Eq(g(x),-.5*x+512)')
    assert not isEquivalent("Eq(w+((w+1)),(Eq(4.5+6,t)))","Eq(t,4.5*w+6)")
    assert isEquivalent("Eq(A,P*(e)**(((k*t))))", "Eq(A,P*(e)**(k*t))")
    assert isEquivalent("Eq(a,p*(e)**(k*t)+t**(c+1)+exp(d)+exp(exp(f)))", "Eq(a,p*(e)**(k*t)+t**(c+1)+exp(d)+exp(exp(f)))")
    assert isEquivalent("Eq(a,p*(e)**(k*t)+t**(c+1)+exp(d)+exp(exp(f)))", "Eq(a,p*((e))**(((k*t)))+t**((c)+1)+exp(d)+exp(exp(f)))")
    assert not isEquivalent("Eq(a,p*(e)**(k*t)+t**(c+1)+exp(d)+exp(exp(f)))", "Eq(a,1+p*((e))**(((k*t)))+t**((c)+1)+exp(d)+exp(exp(f)))")
    assert isEquivalent("Eq(a,p*(e)**(k*t))", "Eq(a,p*(e)**(((k*t))))")


@route('/isequivalent', method='POST')
def checkequivalence():
    response.timeout = 2
    studentResponse = request.forms.get("response")
    exemplar = request.forms.get("exemplar")
    allowSimplification = request.forms.get("simplify") == 'True'
    trig = request.forms.get("trig") == 'True'
    log = request.forms.get("log") == 'True'
    force = request.forms.get("force") == 'True'

    request_id = str(uuid.uuid1())
    logevent('isequivalent entry', locals())

    return_value = '{"result":"error"}'
    try:
        if isEquivalent(studentResponse, exemplar, False, allowSimplification, trig, log, force):
            return_value = '{"result":"true"}'
        else:
            return_value = '{"result":"false"}'
    except TimeoutError:
        traceback.print_exc()
        response.status = 500
        return_value = '{"result":"errorTimeout"}'
    except:
        traceback.print_exc()
        response.status = 500
        return_value = '{"result":"error"}'

    logevent('isequivalent exit', {'request_id': request_id, 'return_value': return_value})
    return return_value

@route('/parsable', method='POST')
def checkparsable():
    response.timeout = 2
    studentResponse = request.forms.get("response")

    request_id = str(uuid.uuid1())
    logevent('parsable entry', locals())

    return_value = '{"result":"error"}'
    try:
        if(parsable(studentResponse)):
            return_value = '{"result":"true"}'
        else:
            return_value = '{"result":"false"}'
    except TimeoutError:
        traceback.print_exc()
        response.status = 500
        return_value = '{"result":"errorTimeout"}'
    except:
        traceback.print_exc()
        response.status = 500
        return_value = '{"result":"error"}'

    logevent('parsable exit', {'request_id': request_id, 'return_value': return_value})
    return return_value

@route('/matchexpression', method='POST')
def checkmatchexpression():
    response.timeout = 2
    studentresponse = request.forms.get("response").strip()
    pattern = request.forms.get("pattern").strip()
    parameters = request.forms.get("parameters").strip().split('|')
    constraints = request.forms.get("constraints").strip().split('|')
    variables = request.forms.get("variables").strip().split('|')

    request_id = str(uuid.uuid1())
    logevent('matchexpression entry', locals())

    return_value = '{"result":"error"}'
    try:
        if variables == ['']:
            variables = []

        if constraints == ['']:
            constraints = []

        if parameters == ['']:
            parameters = []

        result = '|'.join(matchExpression(studentresponse, pattern, parameters, constraints, variables))
        return_value = '{"result":"' + result + '"}'

    except TimeoutError:
        traceback.print_exc()
        response.status = 500
        return_value = '{"result":"errorTimeout"}'

    except:
        traceback.print_exc()
        response.status = 500
        return_value = '{"result":"error"}'

    logevent('matchexpression exit', {'request_id': request_id, 'return_value': return_value})
    return return_value

@route('/matchdouble', method='POST')
def checkmatchdouble():

    response.timeout = 2
    studentresponse = request.forms.get("response").strip()
    pattern = request.forms.get("pattern").strip()
    parameters = request.forms.get("parameters").strip().split('|')
    constraints = request.forms.get("constraints").strip().split('|')
    variables = request.forms.get("variables").strip().split('|')

    request_id = str(uuid.uuid1())
    logevent('matchdouble entry', locals())

    return_value = '{"result":"error"}'
    try:
        if variables == ['']:
            variables = []
            result = '|'.join(matchDouble(studentresponse, pattern, parameters, constraints, variables))
            return_value = '{"result":"' + result + '"}'

    except TimeoutError:
        traceback.print_exc()
        response.status = 500
        return_value = '{"result":"errorTimeout"}'

    except:
        traceback.print_exc()
        response.status = 500
        return_value = '{"result":"error"}'

    logevent('matchdouble exit', {'request_id': request_id, 'return_value': return_value})
    return return_value

@route('/evaluate', method='POST')
def evaluate():
    response.timeout = 2
    studentresponse = request.forms.get("response").strip()

    request_id = str(uuid.uuid1())
    logevent('evaluate entry', locals())

    return_value = '{"result":"error"}'
    try:
        result = evaluateExpression(studentresponse)
        return_value = '{"result":"' + str(result) + '"}'
    except TimeoutError:
        traceback.print_exc
        response.status = 500
        return_value = '{"result":"errorTimeout"}'
    except:
        traceback.print_exc()
        response.status = 500
        return_value = '{"result":"error"}'

    logevent('evaluate exit', {'request_id': request_id, 'return_value': return_value})
    return return_value

#matchDblTest()

#unittestlib()
#assert not
#parsable('(8/(((((48)/(((((444)))**(((((5/(((5)**(((99)))))))))))))))))')
#assert not
#isEquivalent('(8/(((((48)/(((((444)))**(((((5/(((5)**(((99)))))))))))))))))',
#'891/8')

#print parsable('44*s*q*u*a*r*e*u*n*I*t*s')
#print isEquivalent('44*s*q*u*a*r*e*u*n*I*t*s','688')

#print matchExpression('((2+_a4)*_m4)-((2+_a3)*_m3)','a-b',['a','b'],[],[])
##18666
#print matchExpression('2-2','a-b',['a','b'],[],[])

#assert isEquivalent('asin(0.8)', 'asin(0.8)', False, True, True, False, False)
#assert isEquivalent('asin((0.8))', 'asin(0.8)', False, True, True, False,
#False)
host = 'localhost'
port = 8084
debug = True
server = 'cherrypy'

logevent('Starting EqScoringWebService on node %d (%s:%d), debug=%s, server=%s' % (
    uuid.getnode(), host, port, debug, server))
run(host=host, port=port, debug=debug, server=server)

# curl -X POST -F response=1 -F exemplar=1 -F simplify=True -F trig=True -F log=True http://localhost:8080/isequivalent
# curl -X POST -F response=1 http://localhost:8080/parsable
# curl -X POST -F response=2*x+1 -F pattern=a*x+b -F parameters="a|b" -F constraints=" " -Fvariables=x http://localhost:8080/matchexpression
