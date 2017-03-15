# KMeansHelper.R

# Function plots labeled observations and centers in R2
ClusterPlot <- function(observations=sampleObservations, centers=centersGuess, labels=labelsRandom)
{
  # Create the frame of a plot without contents
  x_Min <- min(observations[,1], centers[,1], na.rm=T)
  x_Max <- max(observations[,1], centers[,1], na.rm=T)
  y_Min <- min(observations[,2], centers[,2], na.rm=T)
  y_Max <- max(observations[,2], centers[,2], na.rm=T)
  yLocation <- xLocation <- c()
  plot(c(), xlab='X', ylab='Y', ylim=c(y_Min, y_Max), xlim=c(x_Min, x_Max))
  
  # Add observations
  uniqueLabels <- sort(unique(labels))
  for (label in uniqueLabels)
  {    
    affiliatedObservations <- matrix(data=observations[labels == label,], ncol=ncol(observations))
    if(nrow(affiliatedObservations) > 0)
    {
      points(affiliatedObservations, pch=21, col=label+1, cex=4, lwd=2)
      text(x=affiliatedObservations[,1], y=affiliatedObservations[,2], label, cex=1, col=label+1)
    }
  } # for
  
  # Add centers
  iter <- 1:length(uniqueLabels)
  points(centers, pch=24, cex=5, col=uniqueLabels+1, bg='lightgrey')
  text(x=centers[iter,1], y=centers[iter,2], uniqueLabels, cex=2, col=uniqueLabels+1)
  
  Sys.sleep(1)
} # ClusterPlot


