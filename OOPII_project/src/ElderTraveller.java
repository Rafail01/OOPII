
public class ElderTraveller extends Traveller {

	private double p = 0.20;
	private int maxDist = 15317; // Distance from Athens to Sydney
	
	//constructors
	public ElderTraveller(String name, int age, long timestamp, int[] travellerTermsVector, double[] travellerGeodesicVector) {
		super(name, age, timestamp, travellerTermsVector, travellerGeodesicVector);
	}
	
	public ElderTraveller() {}

	//calculates traveller & city terms vectors similarity
	private double similarityTermsVector(int[] travellerTermsVector, int[] cityTermsVector) {
		double sumIntersection = 0;
		double sumUnion = 0;
		for(int i = 0; i < travellerTermsVector.length; i++) {
			if(travellerTermsVector[i] >= 1 && cityTermsVector[i] >= 1) {
				sumIntersection += 1;
			}
			if(travellerTermsVector[i] >= 1 || cityTermsVector[i] >= 1) {
				sumUnion += 1;
			}
		}
		
		if(sumUnion == 0) {
			return 0;
		}
		
		return sumIntersection / sumUnion;
	}
	
	//calculates traveller & city geodesic vectors similarity
	private double similarityGeodesicVector(double[] travellerGeodesicVector,double[] cityGeodesicVector) {
		return log2(2 / 2-(distance(travellerGeodesicVector[0],travellerGeodesicVector[1],cityGeodesicVector[0],cityGeodesicVector[1],"k") / maxDist));	
	}
	
	//calculates the log base 2 of a number
	private static double log2(double N) {
		double result = (Math.log(N) / Math.log(2));
		return result;
	}
	
	@Override
	public double calculateSimilarity(City city) {
		double similarity = p * similarityTermsVector(getTravellerTermsVector(), city.getCityTermsVector()) + (1-p) * similarityGeodesicVector(getTravellerGeodesicVector(), city.getCityGeodesicVector());
		city.setSimilarity(similarity);
		return similarity;
	}
	
}