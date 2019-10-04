import argparse
import subprocess
import sys
import os

parser = argparse.ArgumentParser()
parser.add_argument("assignment")
parser.add_argument("title")
parser.add_argument("files", default=[], nargs='*')

args   = parser.parse_args()

target_url    = "https://web-cat.cs.vt.edu/Web-CAT/WebObjects/Web-CAT.woa/wa/assignments/eclipse?institution=ChristopherNewport"
submitter     = "webcat-submitter-1.0.4.jar"
login_info    = "login.txt"
source_folder = "../Gradle/src/main/java/"

valid_files   = []
invalid_files = []

for f in args.files:
	if not f in os.listdir(source_folder):
		print('WARNING: I couldn\'t find ' + f)
		invalid_files.append(f)
	else:
		valid_files.append(f)

with open(login_info) as creds:
	temp = []
	for line in creds:
		temp.append(line.strip())

	username = temp[0]
	password = temp[1]

runme = ['java', '-jar', submitter, '-t', target_url, '-u', username, '-p', password, '-a', args.assignment]
# I would have REALLY loved this feature to work =(
#runme = ['java', '-jar', submitter, '-t', target_url, '-l', args.title, '-u', username, '-p', password, '-a', args.assignment]

for f in valid_files:
	runme.append(source_folder + f.strip())

o = ''
try:
	o = subprocess.run(runme, check=True, stdout=subprocess.PIPE).stdout.strip()
except subprocess.CalledProcessError as e:
	print('Something went wrong.')
	print(e.output)
	sys.exit(1)

if 'Your login information could not be validated' in str(o):
	sys.exit('Your login information is incorrect')
else:
	if (len(invalid_files)):
		print(" Submitted without some files:")
		for f in invalid_files:
			print("   "+f)

		sys.exit(2)
	else:
		print('Submission Success!')
