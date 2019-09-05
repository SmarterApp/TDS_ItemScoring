As mentioned in "Running Item Scoring Engine.docx" file,
EqScoringWebService.py is the python scoring script.

## Setup process

### Local development (Mac OS)

**Initializing Python development environment**
You will need Python-2.7.

Set up virtualenv for this project (from within this project root directory)
```bash
python2.7 -m virtualenv venv
```

Activate virtualenv during development
```bash
source venv/bin/activate
```

Install required dependencies
```bash
pip install sympy
pip install cherrypy
pip install -r requirements.txt
```

Sympy has been customized by AIR and is in the site-packages.zip file.
You can install it on the system, or you can run it as follows by setting
PYTHONPATH to the egg file containing the modded Sympy:
$ export PYTHONPATH=/Users/allagorina/development/SB/src/TDS_ItemScoring/sympy-scripts/site-packages.zip

Once development is complete, deactivate virtualenv
```bash
deactivate
```

## Running

```bash
python EqScoringWebService.py
```
### Example of how to test it:
```bash
curl -X POST -F response=2*x+1 -F pattern=a*x+b -F parameters="a|b" -F constraints=" " -Fvariables=x http://localhost:8080/matchexpression
```

# Windows instructions:
  For the customized version of sympy there are no installation files/folders.
  You will see a zip file called site-packages.zip. 
  
  Assuming you have installed Python into C:\Python27, simply unzip the
  contents of site-packages.zip into 'C:\Python27\Lib\site-packages'.
