As mentioned in "Running Item Scoring Engine.docx" file, 
EqScoringWebService.py is the python scoring script.

You will need Python-2.7.  

Install requirements from requirements.txt file as follows:
$ sudo -H pip install -r requirements.txt

Sympy has been customized by AIR and is in the site-packages.zip file.

You can install it on the system, or you can run it as follows by setting
PYTHONPATH to the egg file containing the modded Sympy:

$ export PYTHONPATH=`pwd`/site-packages.zip
$ python EqScoringWebService.py

Windows instructions:
  For the customized version of sympy there are no installation files/folders.
  You will see a zip file called site-packages.zip. 
  
  Assuming you have installed Python into C:\Python27, simply unzip the
  contents of site-packages.zip into 'C:\Python27\Lib\site-packages'.
