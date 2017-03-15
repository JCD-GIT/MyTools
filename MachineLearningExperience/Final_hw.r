#Clear Workspace and Console
rm(list=ls())
cat("\014")

## Import KMeans code
source("C:\\Dados\\jcd\\Data Science\\Parte2\\Projeto_Final\\tools\\KMean_4d.R")

## Path to save images
img_path<-"C:\\Dados\\jcd\\Data Science\\Parte2\\Projeto_Final\\images\\"

## function to read csv data (4 columns) created by parse_txt.exe
read.langdata <- function(path = 'SET-YOUR-PATH-HERE', file = 'SET-YOUR-PATH-HERE')
{
  ## Function to read the csv file
  filePath <- file.path(path, file)
  lang.data <- read.csv(filePath, header = TRUE, 
                         stringsAsFactors = FALSE)
  lang.data
}


## Plot all possible diverse cases for one language
plot.lang <- function (lang='Language',x='data from csv') 
{
png(filename=paste0 (img_path,"FirstChar_",lang,".png"))
hist(x$FirstCharID,main=paste0(lang,"- First Char"))
dev.off()
png(filename=paste (img_path,"LastChar_",lang,".png"))
hist(x$LastCharID,main=paste0(lang,"- Last Char"))
dev.off()
png(filename=paste (img_path,"Size_",lang,".png"))
hist(x$Size,main=paste0(lang,"- Word Size"))
dev.off()
png(filename=paste (img_path,"Ocurrences_",lang,".png"))
hist(x$Occurrences,main=paste0(lang,"- # Occurrences"))
dev.off()
png(filename=paste (img_path,"FirstCharXSize_",lang,".png"))
plot(x$FirstCharID,x$Size,main=paste0(lang,"- First Char X Word Size"))
dev.off()
png(filename=paste (img_path,"FirstCharXOccurrences_",lang,".png"))
plot(x$FirstCharID,x$Occurrences,main=paste0(lang,"- First Char X # Occurrences"))
dev.off()
png(filename=paste (img_path,"LastCharXSize_",lang,".png"))
plot(x$LastCharID,x$Size,main=paste0(lang,"- Last Char X Word Size"))
dev.off()
png(filename=paste (img_path,"LastCharXOccurrences_",lang,".png"))
plot(x$LastCharID,x$Occurrences,main=paste0(lang,"- Last Char X # Occurrences"))
dev.off()
png(filename=paste (img_path,"SizeXOcurrences_",lang,".png"))
plot(x$Size,x$Occurrences,main=paste0(lang,"- Word Size X # Occurrences"))
dev.off()
png(filename=paste (img_path,"FirstCharXLastChar_",lang,".png"))
plot(x$FirstCharID,x$LastCharID,main=paste0(lang,"- First Char X Last Char"))
dev.off()
}

## Process all languages
proc.langs <- function (csvfile='FileName')
{
x<-read.langdata("C:\\Dados\\jcd\\Data Science\\Parte2\\Projeto_Final\\pt-pt",csvfile)
plot.lang("Portuguese",x)
x<-read.langdata("C:\\Dados\\jcd\\Data Science\\Parte2\\Projeto_Final\\es-es",csvfile)
plot.lang("Spanish",x)
x<-read.langdata("C:\\Dados\\jcd\\Data Science\\Parte2\\Projeto_Final\\fr-fr",csvfile)
plot.lang("French",x)
x<-read.langdata("C:\\Dados\\jcd\\Data Science\\Parte2\\Projeto_Final\\de-de",csvfile)
plot.lang("German",x)
x<-read.langdata("C:\\Dados\\jcd\\Data Science\\Parte2\\Projeto_Final\\en-us",csvfile)
plot.lang("English",x)
}


## Find the clusters for this file
find_clusters <- function (lang='Language Folder',csvfile='FileName', ic="initial_cluster")
{
z<-read.langdata(paste0("C:\\Dados\\jcd\\Data Science\\Parte2\\Projeto_Final\\",lang),csvfile)
w<-c(z[,1],z[,2],z[,5],z[,6])
v<-matrix(w,ncol=4)
## Normalizing column 3 and 4 to same range of column 1 and 2
v[,3]<-v[,3]*20/max(v[,3]) 
v[,4]<-v[,4]*20/max(v[,4])
x<- KMeans (observations = v, clusterCenters = initial_cluster)
x
}





## initial_cluster <- matrix(c(65,70,75,80,85,65,85,75,85,65,0,0,0,0,0,0,0,0,0,0), ncol=4)
## initial_cluster <- matrix(c(67,75,82,80,70,67,75,82,70,80,2,7,12,3,14,1,7,15,11,4), ncol=4)
## initial_cluster <- matrix(c(67,75,82,80,70,67,75,82,70,80,2,7,10,5,9,1,2,3,4,5), ncol=4)
## initial_cluster <- matrix(c(67,75,82,67,75,82,1,3,5,3,2,1), ncol=4)
## initial_cluster <- matrix(c(65,70,75,80,85,65,85,75,85,65,0,4,2,4,0,0,4,2,4,0), ncol=4)
## initial_cluster <- matrix(c(65,70,75,80,85,65,85,75,85,65,0,1,2,1,0,1,0,2,0,1), ncol=4)
## initial_cluster <- matrix(c(65,70,80,85,65,85,85,65,0,4,4,0,0,4,4,0), ncol=4)

initial_cluster <- matrix(c(65,70,75,80,85,65,85,75,85,65,0,0,4,4,2,2,4,4,0,0), ncol=4)



## For each language and file, calculate the clusters and keep in a list

   Languages<-c("English","German","Spanish","French","Portuguese")
   Langs<-c("en-us","de-de","es-es","fr-fr","pt-pt")
## Files<-c("5receipts.csv","carapaus_alimados.csv")
## Files<-c("5receipts.csv","pescada_assada.csv")
## Files<-c("5receipts.csv","3news.csv")
## Files<-c("3news.csv","other_new.csv")
   Files<-c("7receipts.csv","2receipts.csv")
 
## Languages<-c("Italian","German","Spanish","French","Portuguese")
## Langs<-c("it-it","de-de","es-es","fr-fr","pt-br")
## Files<-c("learning.csv","test.csv")

## Process 2 files to be used in study   
 proc.langs(Files[1])
 proc.langs(Files[2])
 
## Create list to keep results  
 Result<-list(initial_cluster)

## Find cluster points to each file and each language and save Results  
 f_index<-1
 for (f in Files)
 {
   l_index<-1
   for (l in Langs)
   {
     Result[l_index+(f_index-1)*length(Langs)]<-list(find_clusters(lang=l,csvfile=f,ic=initial_cluster))
     l_index<-l_index+1
   }
   f_index<-f_index+1
 }
 
 
 ## Calculate the distance between points of different files 
 
 k<-1
 dist_result<-0
 for (i in (1:length(Result)))
  {
   for (j in (1:length(Result)))
    {
     ## Convert index from Result to Langs and Files index
     if ((i%%length(Langs))!=0) lang_i<-Langs[i%%length(Langs)]
     if ((j%%length(Langs))!=0) lang_j<-Langs[j%%length(Langs)]
     if ((i%%length(Langs))==0) lang_i<-Langs[length(Langs)]
     if ((j%%length(Langs))==0) lang_j<-Langs[length(Langs)]
     file_i<-Files[1+(i-1)%/%length(Langs)]
     file_j<-Files[1+(j-1)%/%length(Langs)]
     if (file_i!=file_j) 
      {
       ## Print distances between points
       print(paste0("distance - language: ",lang_i," file: ",file_i," X language: ",lang_j," file: ",file_j))
       x<-sqrt(sum((Result[[i]]-Result[[j]])^2))
       print (x)
       dist_result[k]<-x
       k<-k+1
      }
    }
  }
 
 
 ## Plot data to show distances in a graph for all languages 
 k<-1
 for (l in Languages)
 {
   png(filename=paste0(img_path,"Comparing ",l, " with other Languages.png"))
   plot(dist_result[((k-1)*5+1):(5*k)], main=paste0("Comparing ",l,"(",as.name(k),") with other Languages"), cex=3, col="red", ylab="distance", xlab="Languages")
   dev.off()
   k<-k+1
 }

## Function to generate table char-dec for all chars used for reference 
ascii<- function()
 {
  for (i in 64:90)
   {
    if (i==64) print("Char     Dec")
    print(paste0(rawToChar(as.raw(i)),"        ",as.character(i)))
   }
 }  
