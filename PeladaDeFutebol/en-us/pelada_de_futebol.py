
from PyQt4.QtCore import *
from PyQt4.QtGui import *
import sys

# Tool para peladas de futebol
# Desenvolvido por Jose' Carlos Duarte
# v 1.0 - 24, Novembro, 2015

class Tela1(QDialog):

    Quantos = 0
    def __init__(self):
        QDialog.__init__(self)




        self.layout = QVBoxLayout()

        label = QLabel("How many teams?")
        self.line_edit = QLineEdit()
        botaoC = QPushButton("Continue")
        botaoF = QPushButton("Close")

        self.layout.addWidget(label)
        self.layout.addWidget(self.line_edit)
        self.layout.addWidget(botaoC)
        self.layout.addWidget(botaoF)

        self.setLayout(self.layout)

        self.setWindowTitle("League")


        botaoF.clicked.connect(self.close)
        botaoC.clicked.connect(self.selecionado)

        #self.line_edit.textEdited.connect(self.selecionado)


    def selecionado(self):


        Tela1.Quantos = int(self.line_edit.text())
        if (Tela1.Quantos<2):
            Tela1.Quantos=2
        if (Tela1.Quantos>7):
            Tela1.Quantos=7

        self.close()








class Tela2(QDialog):

    Times=['Team 0','Team 1','Team 2','Team 3','Team 4','Team 5','Team 6', 'Team 7']
    Turno=1
    Prev_Estatistica=[ [ 0 for i in range(5) ] for j in range(10) ]
    def __init__(self):
        QDialog.__init__(self)

        self.total=Tela1.Quantos

        for i in range(1,self.total+1):
            Tela2.Prev_Estatistica[i][0]=0
            Tela2.Prev_Estatistica[i][1]=0
            Tela2.Prev_Estatistica[i][2]=0
            Tela2.Prev_Estatistica[i][3]=0
            Tela2.Prev_Estatistica[i][4]=0

        self.layout = QVBoxLayout()

        Tela2.Times[0]=0


        self.total=Tela1.Quantos


        label7= QLabel("Team 7: ")
        label6= QLabel("Team 6: ")
        label5= QLabel("Team 5: ")
        label4= QLabel("Team 4: ")
        label3= QLabel("Team 3: ")
        label2= QLabel("Team 2: ")
        label1= QLabel("Team 1: ")


        if (self.total>=7): self.Line_edit_7 = QLineEdit()
        if (self.total>=6): self.Line_edit_6 = QLineEdit()
        if (self.total>=5): self.Line_edit_5 = QLineEdit()
        if (self.total>=4): self.Line_edit_4 = QLineEdit()
        if (self.total>=3): self.Line_edit_3 = QLineEdit()
        if (self.total>=2): self.Line_edit_2 = QLineEdit()
        if (self.total>=1): self.Line_edit_1 = QLineEdit()

        if (self.total>=7): Tela2.Times[7] = self.Line_edit_7.text()
        if (self.total>=6): Tela2.Times[6] = self.Line_edit_6.text()
        if (self.total>=5): Tela2.Times[5] = self.Line_edit_5.text()
        if (self.total>=4): Tela2.Times[4] = self.Line_edit_4.text()
        if (self.total>=3): Tela2.Times[3] = self.Line_edit_3.text()
        if (self.total>=2): Tela2.Times[2] = self.Line_edit_2.text()
        if (self.total>=1): Tela2.Times[1] = self.Line_edit_1.text()



        if (self.total>=1):
            self.layout.addWidget(label1)
            self.layout.addWidget(self.Line_edit_1)
        if (self.total>=2):
            self.layout.addWidget(label2)
            self.layout.addWidget(self.Line_edit_2)
        if (self.total>=3):
            self.layout.addWidget(label3)
            self.layout.addWidget(self.Line_edit_3)
        if (self.total>=4):
            self.layout.addWidget(label4)
            self.layout.addWidget(self.Line_edit_4)
        if (self.total>=5):
            self.layout.addWidget(label5)
            self.layout.addWidget(self.Line_edit_5)
        if (self.total>=6):
            self.layout.addWidget(label6)
            self.layout.addWidget(self.Line_edit_6)
        if (self.total>=7):
            self.layout.addWidget(label7)
            self.layout.addWidget(self.Line_edit_7)

        botaoC = QPushButton("Continue")
        botaoF = QPushButton("Close")

        self.layout.addWidget(botaoC)
        self.layout.addWidget(botaoF)

        self.setLayout(self.layout)

        self.setWindowTitle("Teams")

        botaoF.clicked.connect(self.close)
        botaoC.clicked.connect(self.selecionado)

        #self.line_edit.textEdited.connect(self.selecionado)


    def selecionado(self):


        if (self.total>=7): Tela2.Times[7] = self.Line_edit_7.text()
        if (self.total>=6): Tela2.Times[6] = self.Line_edit_6.text()
        if (self.total>=5): Tela2.Times[5] = self.Line_edit_5.text()
        if (self.total>=4): Tela2.Times[4] = self.Line_edit_4.text()
        if (self.total>=3): Tela2.Times[3] = self.Line_edit_3.text()
        if (self.total>=2): Tela2.Times[2] = self.Line_edit_2.text()
        if (self.total>=1): Tela2.Times[1] = self.Line_edit_1.text()

        Tela2.Times[0]=1

        self.close()



class Tela3(QDialog):

    def __init__(self):
        QDialog.__init__(self)

        self.layout = QGridLayout()


        k=0
        last=-1

        jogos=[ [ 0 for i in range(2) ] for j in range(50) ]
        self.seq_jogos=[ [ 0 for i in range(3) ] for j in range(50) ]
        self.edit_line=[ [0 for i in range(2) ] for j in range (50)]
        label=[ [ 0 for i in range(2) ] for j in range(50) ]
        self.Pontos_Gols=[ [ 0 for i in range(2) ] for j in range(10) ]
        self.Estatistica=[ [ 0 for i in range(5) ] for j in range(10) ]
        labelT=[0 for j in range(10) ]
        self.Numeros_T=[0 for j in range(10) ]
        self.Numeros=[0 for j in range(10) ]


        for i in range(1,Tela1.Quantos+1):
            for j in range(i+1,Tela1.Quantos+1):
                jogos[k][0]=i
                jogos[k][1]=j
                k=k+1

        time1=0
        time2=0
        time1b=0
        time2b=0
        j=0
        while (j<k):
            for i in range (0,k):

                if ((i==last) & (time1b==0)):
                    time1=0
                    time2=0

                if (i==last):
                    time1b=0
                    time2b=0

                if ((jogos[i][0]!=time1) & (jogos[i][1]!=time1) & (jogos[i][0]!=time1b) & (jogos[i][1]!=time1b) &(jogos[i][0]!=time2) & (jogos[i][1]!=time2) & (jogos[i][0]!=time2b) & (jogos[i][1]!=time2b)  & (jogos[i][0]!=0) & (jogos[i][1]!=0)) :
                    self.seq_jogos[j][0]=jogos[i][0]
                    self.seq_jogos[j][1]=jogos[i][1]
                    self.seq_jogos[j][2]=j+1
                    time1b=time1
                    time2b=time2
                    time1=jogos[i][0]
                    time2=jogos[i][1]

                    last=i

                    self.temp=QLineEdit()
                    self.temp.textChanged.connect(self.mudou_placar)
                    self.temp.setAlignment(Qt.AlignCenter)
                    self.edit_line[j][0]= self.temp

                    label[j][0] = QLabel(Tela2.Times[jogos[i][0]])
                    self.temp=QLabel(Tela2.Times[jogos[i][0]])
                    self.temp.setAlignment(Qt.AlignRight)
                    label[j][0]=self.temp
                    label[j][1] = QLabel(Tela2.Times[jogos[i][1]])


                    labelX= QLabel(" X ")

                    self.temp=QLineEdit()
                    self.temp.textChanged.connect(self.mudou_placar)
                    self.temp.setAlignment(Qt.AlignCenter)
                    self.edit_line[j][1]= self.temp
                    self.layout.addWidget(self.edit_line[j][0],j,1)
                    self.layout.addWidget(label[j][0],j,0)
                    self.layout.addWidget(labelX,j,2)
                    self.layout.addWidget(label[j][1],j,4)
                    self.layout.addWidget(self.edit_line[j][1],j,3)
                    jogos[i][0]=0
                    jogos[i][1]=0
                    j=j+1



        temp=QLabel()
        self.layout.addWidget(temp)
        temp=QLabel()
        self.layout.addWidget(temp)
        temp=QLabel()
        self.layout.addWidget(temp)
        temp=QLabel()
        self.layout.addWidget(temp)
        temp=QLabel()
        self.layout.addWidget(temp)

        total = Tela1.Quantos
        temp=QLabel("Team")
        self.layout.addWidget(temp)
        temp=QLabel("Points")
        self.layout.addWidget(temp)
        temp=QLabel()
        self.layout.addWidget(temp)
        temp=QLabel("Gols")
        self.layout.addWidget(temp)
        temp=QLabel("P / G / GD / W / G")
        self.layout.addWidget(temp)




        for i in range(1,total+1):

            labelT[i]=QLabel(Tela2.Times[i])
            self.layout.addWidget(labelT[i])
            self.Pontos_Gols[i][0]= QProgressBar()
            self.Pontos_Gols[i][0].setRange(0,(total-1)*3*Tela2.Turno)
            self.Pontos_Gols[i][0].setAlignment(Qt.AlignCenter)
            self.Pontos_Gols[i][0].setTextVisible(False)
            self.Pontos_Gols[i][0].setValue(Tela2.Prev_Estatistica[i][0]*3+Tela2.Prev_Estatistica[i][1])
            self.layout.addWidget(self.Pontos_Gols[i][0])
            temp=QLabel()
            self.layout.addWidget(temp)
            self.Pontos_Gols[i][1]= QProgressBar()
            self.Pontos_Gols[i][1].setRange(0,(total-1)*3*Tela2.Turno)
            self.Pontos_Gols[i][1].setAlignment(Qt.AlignCenter)
            self.Pontos_Gols[i][1].setTextVisible(False)
            self.Pontos_Gols[i][1].setValue(Tela2.Prev_Estatistica[i][3])
            self.layout.addWidget(self.Pontos_Gols[i][1])
            #self.Numeros[i]="0 / 0 / 0 / 0 / 0"
            pontos=Tela2.Prev_Estatistica[i][0]*3+Tela2.Prev_Estatistica[i][1]
            gols=Tela2.Prev_Estatistica[i][3]
            saldo=Tela2.Prev_Estatistica[i][3]-Tela2.Prev_Estatistica[i][4]
            vitorias=Tela2.Prev_Estatistica[i][0]
            jogos=Tela2.Prev_Estatistica[i][0]+Tela2.Prev_Estatistica[i][1]+Tela2.Prev_Estatistica[i][2]
            self.Numeros[i]=str(pontos) + " / " + str(gols) + " / " + str(saldo) + " / " + str(vitorias) + " / " + str(jogos)
            self.Numeros_T[i]=QLabel(self.Numeros[i])
            self.layout.addWidget(self.Numeros_T[i])








        temp=QLabel()
        self.layout.addWidget(temp)
        temp=QLabel()
        self.layout.addWidget(temp)
        temp=QLabel()
        self.layout.addWidget(temp)
        temp=QLabel()
        self.layout.addWidget(temp)
        temp=QLabel()
        self.layout.addWidget(temp)
        temp=QLabel()
        self.layout.addWidget(temp)
        self.botaoT = QPushButton("Another Round")

        self.botaoT.setEnabled(False)
        self.layout.addWidget(self.botaoT)
        temp=QLabel()
        self.layout.addWidget(temp)
        self.botaoF = QPushButton("Terminate")
        self.botaoF.setEnabled(False)
        self.layout.addWidget(self.botaoF)

        self.botaoF.clicked.connect(self.close)
        self.botaoT.clicked.connect(self.outro_turno)

        self.setLayout(self.layout)

        self.setWindowTitle("Table games")

    def outro_turno(self):

        Tela2.Turno=Tela2.Turno+1
        total = Tela1.Quantos
        for i in range(1,total+1):
            Tela2.Prev_Estatistica[i][0]=self.Estatistica[i][0]
            Tela2.Prev_Estatistica[i][1]=self.Estatistica[i][1]
            Tela2.Prev_Estatistica[i][2]=self.Estatistica[i][2]
            Tela2.Prev_Estatistica[i][3]=self.Estatistica[i][3]
            Tela2.Prev_Estatistica[i][4]=self.Estatistica[i][4]

        self.close()


    def mudou_placar(self):

        total = Tela1.Quantos

        for i in range(1,total+1):
            self.Estatistica[i][0]=Tela2.Prev_Estatistica[i][0]
            self.Estatistica[i][1]=Tela2.Prev_Estatistica[i][1]
            self.Estatistica[i][2]=Tela2.Prev_Estatistica[i][2]
            self.Estatistica[i][3]=Tela2.Prev_Estatistica[i][3]
            self.Estatistica[i][4]=Tela2.Prev_Estatistica[i][4]



        k=0
        max_avg_gol=1
        todos_jogos=1
        for i in range(1,total+1):
            for j in range(i+1,total+1):
                if self.edit_line[k][0].text()=='':
                    P1=-1
                    todos_jogos=0
                else:
                    P1=int(self.edit_line[k][0].text())
                if self.edit_line[k][1].text()=='':
                    P2=-1
                    todos_jogos=0
                else:
                    P2=int(self.edit_line[k][1].text())


                if ((P1>=0) & (P1<=10000) & (P2>=0) & (P2<=10000)):
                    time1=int(self.seq_jogos[k][0])
                    time2=int(self.seq_jogos[k][1])

                    if (P1 > P2):
                        self.Estatistica[time1][0]=self.Estatistica[time1][0]+1
                        self.Estatistica[time2][2]=self.Estatistica[time2][2]+1
                    if (P1 < P2):
                        self.Estatistica[time2][0]=self.Estatistica[time2][0]+1
                        self.Estatistica[time1][2]=self.Estatistica[time1][2]+1
                    if (P1 == P2):
                        self.Estatistica[time2][1]=self.Estatistica[time2][1]+1
                        self.Estatistica[time1][1]=self.Estatistica[time1][1]+1
                    self.Estatistica[time1][3]=self.Estatistica[time1][3]+P1
                    self.Estatistica[time1][4]=self.Estatistica[time1][4]+P2
                    self.Estatistica[time2][3]=self.Estatistica[time2][3]+P2
                    self.Estatistica[time2][4]=self.Estatistica[time2][4]+P1

                k=k+1


        for i in range(1,total+1):
            pontos=self.Estatistica[i][0]*3+self.Estatistica[i][1]
            gols=self.Estatistica[i][3]
            saldo=self.Estatistica[i][3]-self.Estatistica[i][4]
            vitorias=self.Estatistica[i][0]
            jogos=self.Estatistica[i][0]+self.Estatistica[i][1]+self.Estatistica[i][2]

            self.Numeros_T[i].setText(str(pontos) + " / " + str(gols) + " / " + str(saldo) + " / " + str(vitorias) + " / " + str(jogos))
            if (jogos >0):
                if ((gols/jogos) > max_avg_gol):
                    max_avg_gol=gols/jogos




        for i in range(1,total+1):
            self.Pontos_Gols[i][0].setValue(self.Estatistica[i][0]*3+self.Estatistica[i][1])
            self.Pontos_Gols[i][1].setRange(0,(total*Tela2.Turno)*max_avg_gol)
            self.Pontos_Gols[i][1].setValue(self.Estatistica[i][3])


        if (todos_jogos==1):
            self.botaoF.setEnabled(True)
            self.botaoT.setEnabled(True)
        else:
            self.botaoF.setEnabled(False)
            self.botaoT.setEnabled(False)



ap = QApplication(sys.argv)


x=7
dialogo = Tela1()

dialogo.show()

ap.exec_()



if (Tela1.Quantos>1):
    dialogo2 = Tela2()
    dialogo2.show()
    ap.exec_()


if (Tela2.Times[0]==1):
    Turno=0
    while (Turno<Tela2.Turno):
        Turno=Tela2.Turno
        dialogo3 = Tela3()
        dialogo3.show()
        ap.exec()




