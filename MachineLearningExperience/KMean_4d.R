# KMeans_Skeleton.R

# Obtain ClusterPlot.  
source("C:\\Dados\\jcd\\Data Science\\Parte2\\Projeto_Final\\tools\\KMeansHelper.R")


# KMeans_4d is a 4D K-means implementation plotting in 2d
# The function are observations that will be clustered and initial clusterCenters. 
# returns K-mean clusterCenters
# The function does not normalize the inputs. 
KMeans <- function(observations = sampleObservations, clusterCenters = centersGuess)
{   
  # Initialize the cluster labels from the previous iteration with NULL
  previousLabels<-NULL;
  
  # repeat the following processes using a loop.  Prevent infinite loop with a for loop of 25 iterations
  for (i in 1:50)
  {
    # For each observation find the label of its closest cluster center
    currentLabels <- findLabelOfClosestCluster(observations, clusterCenters)
    # Plot observations and clusterCenters
    ClusterPlot(observations, clusterCenters, currentLabels)
    print(clusterCenters)
    # If there was no change in cluster labels, then break
    if (length(previousLabels!=0))
    { 
      test<-sort(unique(currentLabels==previousLabels))
      if (test[1]) 
      {
        break
      }
    }
    # For each cluster of observations determine its center
    clusterCenters <- calculateClusterCenters (observations, currentLabels)
    # Plot observations and clusterCenters
    ClusterPlot(observations, clusterCenters,  currentLabels)
    # remember currentLabels before currentLabels is re-assigned in the next iteration
    previousLabels <- currentLabels
  } # end of the for loop
  # Return the clusterCenters
  clusterCenters
} # end of KMeans.

# For each cluster of observations determine its center
# The inputs are the observations and the cluster labels of the observations
# The output is a vector of the new clusterCenters
calculateClusterCenters <- function(observations=sampleObservations, clusterLabels=labelsRandom)
{
  # How many clusterCenters will we make?  What is the maximum cluster label? 
  number_clust_center<-length(unique(clusterLabels))
  # Create a matrix where each row is a cluster center.  The number of columns reflects the dimensionality of the space.
  cluster_center_coord <- matrix(nrow=number_clust_center, ncol=ncol(observations))
  # For loop through each cluster label 
  for (cc in 1:number_clust_center)
  {
    # Determine the mean of that cluster in the 1st dimension and assign this mean
    # to the all dimensions of the center
    if (length(clusterLabels[clusterLabels==cc])==1)
    {
      cluster_center_coord[cc,1]<-observations[clusterLabels==cc][1]
      cluster_center_coord[cc,2]<-observations[clusterLabels==cc][2]
      cluster_center_coord[cc,3]<-observations[clusterLabels==cc][3]
      cluster_center_coord[cc,4]<-observations[clusterLabels==cc][4]
    }
    
    else
    {
      cluster_center_coord[cc,1]<-mean(observations[clusterLabels==cc,][,1])
      cluster_center_coord[cc,2]<-mean(observations[clusterLabels==cc,][,2])
      cluster_center_coord[cc,3]<-mean(observations[clusterLabels==cc,][,3])
      cluster_center_coord[cc,4]<-mean(observations[clusterLabels==cc,][,4])
    }
  } # Ends the for loop through each cluster id
  # Return the clusterCenters
  cluster_center_coord
} # end of calculateClusterCenters

# A function that returns the cluster IDs for each observation
# The function takes the observations
# The function takes clusterCenters 
# The cluster that is closest to each observation will determine the cluster ID for that observation
# A cluster ID indicates the allegiance of a observation to a cluster
findLabelOfClosestCluster <- function(observations = sampleObservations, clusterCenters=centersGuess)
{
  # Get the number of clusterCenters
  numberOfClusters <- nrow(clusterCenters)
  # Get the number of observations
  numberOfObservations <- nrow(observations)
  # Create a matrix that will contain the squared distances from each observation to each center
  # The matrix has numberOfObservations rows and numberOfClusters columns
  distances <- matrix(nrow=numberOfObservations, ncol=numberOfClusters)
  # Determine the distance from the center to each observation
  # For loop for each observation number
  for (observationNo in 1:numberOfObservations)
  {
    # For loop for each center number
    for (centerNo in 1:numberOfClusters)
    {
      # What is the difference between the current observation and the current center?
      # In other words: What is the vector between the observation and center?
      diffObsAndCenter <- observations[observationNo, ] - clusterCenters[centerNo, ]
      # What is the distance of this vector?
      # In other words: what is the sum of the squares of the vector elements?
      distance <- sqrt(sum(diffObsAndCenter^2))
      # If the distance squared was NA then make it infinite
      if (is.na(distance)) distance <- Inf
      # Assign the distance to the proper element in the matrix created above
      distances[observationNo, centerNo] <- distance
    } # end of the for loop for each center number
  } # end of the for loop for each observation number
  # Determine the labels of the closest center
  max.col(-distances)
} # end of findLabelOfClosestCluster

