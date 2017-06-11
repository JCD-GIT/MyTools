Readme.txt

* Use Generate_passwords to create strong passwords base in a customized input

* To run is simple, run from command prompt:

Generate_passwords  chars  numbers   symbols

Note:

- chars should contain only chars from a to z or A to Z.
- numbers should contain chars from 0 to 9.
- symbols should not contain chars from 0 to 9, a to z or A to Z.

See below a sample of the tool running:

C:\GeneratePassord>Generate_passwords.exe Teste 123 *$
Te123*$ste
C:\GeneratePassord>Generate_passwords.exe Teste 123 *$
te$123sT*e
C:\GeneratePassord>Generate_passwords.exe Teste 123 *$
123T*e$ste
C:\GeneratePassord>Generate_passwords.exe Teste 123 *$
*t$Est123e
C:\GeneratePassord>Generate_passwords.exe Teste 123 *$
t*$Est123e

Like you can see above, every time a different password is generated. Strong but easy to remember