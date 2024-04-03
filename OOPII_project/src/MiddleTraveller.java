
public class MiddleTraveller extends Traveller {

	private double p = 0.50;
	private int maxDist = 15317; // Distance from Athens to Sydney
	
	//constructors
	public MiddleTraveller(String name, int age, long timestamp, int[] travellerTermsVector, double[] travellerGeodesicVector) {
		super(name, age, timestamp, travellerTermsVector, travellerGeodesicVector);
	}
	
	public MiddleTraveller() {}

	//calculates traveller & city terms vectors similarity
	private double similarityTermsVector(int[] travellerTermsVector, int[] cityTermsVector) {
		double sum = 0;
		double sumA = 0;
		double sumB = 0;
		for(int i = 0; i < travellerTermsVector.length; i++) {
			sum += travellerTermsVector[i] * cityTermsVector[i];
			sumA += Math.pow(travellerTermsVector[i], 2);
			sumB += Math.pow(cityTermsVector[i], 2);
		}
		
		if(sumA == 0 || sumB == 0) {
			return 0;
		}
		
		return sum / (Math.sqrt(sumA) * Math.sqrt(sumB));
	}
	
	//calculates traveller & city geodesic vectors similarity
	private double  similarityGeodesicVector(double[] travellerGeodesicVector,double[] cityGeodesicVector) {
		return log2(2 /2-(distance(travellerGeodesicVector[0],travellerGeodesicVector[1],cityGeodesicVector[0],cityGeodesicVector[1],"K") / maxDist));
				
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