import java.util.ArrayList;

import model.Taxi;

public class TestSelectionSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Taxi> taxiList = new ArrayList<Taxi>();
		Taxi taxi;

		System.out.println(Math.random()*500);
		System.out.println(Math.random()*500);
		System.out.println(Math.random()*500);
		System.out.println(Math.random()*500);
		System.out.println(Math.random()*500);
		System.out.println(Math.random()*500);
		System.out.println(Math.random()*500);
		System.out.println(Math.random()*500);
		
		
		taxi = new Taxi("hej1", "1234,5678");
		taxi.setHeuristic(789);
		taxiList.add(taxi);
		taxi = new Taxi("hej2", "1234,5678");
		taxi.setHeuristic(456);
		taxiList.add(taxi);
		taxi = new Taxi("hej3", "1234,5678");
		taxi.setHeuristic(123);
		taxiList.add(taxi);
		taxi = new Taxi("hej4", "1234,5678");
		taxi.setHeuristic(147);
		taxiList.add(taxi);
		taxi = new Taxi("hej5", "1234,5678");
		taxi.setHeuristic(258);
		taxiList.add(taxi);
		taxi = new Taxi("hej6", "1234,5678");
		taxi.setHeuristic(369);
		taxiList.add(taxi);
		taxi = new Taxi("hej7", "1234,5678");
		taxi.setHeuristic(741);
		taxiList.add(taxi);
		taxi = new Taxi("hej8", "1234,5678");
		taxi.setHeuristic(852);
		taxiList.add(taxi);
		taxi = new Taxi("hej9", "1234,5678");
		taxi.setHeuristic(963);
		taxiList.add(taxi);
		
		for(int i=0; i<taxiList.size(); i++) {
			System.out.println(taxiList.get(i).getTaxiID() + "  " + taxiList.get(i).getHeuristic());
		}
		
		System.out.println();
		taxiList = sortTaxis(taxiList);
		System.out.println();
		
		for(int i=0; i<taxiList.size(); i++) {
			System.out.println(taxiList.get(i).getTaxiID() + "  " + taxiList.get(i).getHeuristic());
		}
	}

	public static ArrayList<Taxi> sortTaxis(ArrayList<Taxi> taxiList) {
		Taxi tempTaxi;
		
		for (int i = 0; i < taxiList.size(); i++) {
			for (int j = 0; j < taxiList.size(); j++) {
				if(taxiList.get(i).getHeuristic() < taxiList.get(j).getHeuristic()) {
					tempTaxi = taxiList.get(i);
					taxiList.set(i, taxiList.get(j));
					taxiList.set(j, tempTaxi);
				}
			}
		}

		return taxiList;
	}

}
