import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;

public class TestDriver {
	
	private static ArrayList<Component> parts;
	
	public static void main(String[] args) {
		
		try {
			
			Scanner sc = new Scanner(new File("storage.txt"));
			
			parts = newStorage(sc);
			ArrayList<Wait> allOrders = new ArrayList<>();
			ArrayList<Ship> shippedOrders = new ArrayList<>();
			ArrayList<File> orders = new ArrayList<>(Arrays.asList(new File("OrderZach.txt"), new File("OrderHiro.txt")));
			
			printStorage(parts);

			for(int i = 0; i < orders.size(); i++) {
				Scanner o = new Scanner(orders.get(i));
				allOrders.add(orderShoe(o, parts));
			}
			
			for(int i = 0; i < allOrders.size(); i++) {
				String n = generateTrackingNumber();
				Ship s = (Ship)allOrders.get(i);
				s.setTrackingNumber(n);
				shippedOrders.add(s);
			}
				
			for(int i = 0; i < shippedOrders.size(); i++) {
				System.out.println(shippedOrders.get(i).toString());
			}
			printStorage(parts);
			
			
			
			
			
		}  catch(FileNotFoundException e) {
			System.out.println("Specified file could not be located.");
		} 
		
		
	}
	
	public static ArrayList<Component> newStorage(Scanner sc) {
		
		ArrayList<Component> parts = new ArrayList<>();
			
			while(sc.hasNextLine()) {
				
				String line = sc.nextLine();
				String[] token = line.split(" ");
				
				if (token[0].equals("L")) {
					for(int i = 0; i < Integer.parseInt(token[1]); i++) {
						parts.add(new Lace(token[2], Double.parseDouble(token[3])));
					}
				}  else if(token[0].equals("S")) {
					for(int i = 0; i < Integer.parseInt(token[1]); i++) {
						parts.add(new Sole(token[2], Double.parseDouble(token[3]), token[4]));
					}
				}  else if(token[0].contentEquals("B")) {
					for(int i = 0; i < Integer.parseInt(token[1]); i++) {
						parts.add(new Body(token[2], Double.parseDouble(token[3]),token[4], token[5]));
					}
				} else {
					System.out.println("Invalid component type stored in file.");
				}
			}
		
		return parts;
		
	}
	
	public static void printStorage(ArrayList<Component> p) {
		
		for(int i = 0; i < p.size(); i++) {
			if (p.get(i) instanceof Lace) {
				Component temp = p.get(i);
				Lace l = (Lace)temp;
				System.out.println(l.toString());
			}  else if (p.get(i) instanceof Sole) {
				Component temp = p.get(i);
				Sole s = (Sole)temp;
				System.out.println(s.toString());
			}  else if (p.get(i) instanceof Body) {
				Component temp = p.get(i);
				Body b = (Body)temp;
				System.out.println(b.toString());
			}
		}
		
	}
	
	public static Wait orderShoe(Scanner sc, ArrayList<Component> c) {
		
		
		Wait newOrder = new Wait();
		ArrayList<Shoe> cart = new ArrayList<>();
		ArrayList<Component> removal = new ArrayList<>();
			
			String line = sc.nextLine();
			String[] token = line.split(" ");
			double total = 0.00;
			
			newOrder.setCustomer(token[0]);
			
			line = sc.nextLine();
			token = line.split(" ");
			
			String choice = token[0];
			
			while(sc.hasNextLine()) {
				
				line = sc.nextLine();
				token = line.split(" ");
				
				Lace l = new Lace(token[0], Double.parseDouble(token[1]));
				Sole s = new Sole(token[2], Double.parseDouble(token[3]), token[4]);
				Body b =  new Body(token[5], Double.parseDouble(token[6]), token[7], token[8]);
				
				boolean inStock = checkInStock(l,s,b);
				if (inStock == true) {
					cart.add(new Shoe(l,s,b));
					removeComponent(l,s,b);
					total += 30.00;
				}  else if (choice.equalsIgnoreCase("yes")) {
					resupply(l,s,b);
					cart.add(new Shoe(l,s,b));
					removeComponent(l,s,b);
					total += 30.00;
				}  else {
					resupply(l,s,b);
				}
				
			}
			
			newOrder.setCart(cart);
			newOrder.setTotal(total);
		
		return newOrder;
		
	}
	
	public static void removeComponent(Lace l, Sole s, Body b) {
		
		int endL = 0;
		int endS = 0;
		int endB = 0;
		
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i) instanceof Lace && endL != 1) {
				Lace test = (Lace)parts.get(i);
				if (l.getColor().equals(test.getColor()) && l.getLength() == test.getLength()) {
					parts.remove(i);
					endL++;
				}
			}  else if (parts.get(i) instanceof Sole && endS != 1) {
				Sole test = (Sole)parts.get(i);
				if (s.getColor().equals(test.getColor()) && s.getSize() == test.getSize() && s.getPattern().equals(test.getPattern())) {
					parts.remove(i);
					endS++;
				}
			}  else if (parts.get(i) instanceof Body && endB != 1) {
				Body test = (Body)parts.get(i);
				if (b.getColor().equals(test.getColor()) && b.getSize() == test.getSize() && b.getPattern().equals(test.getPattern()) && b.getMaterial().equals(test.getMaterial())) {
					parts.remove(i);
					endB++;
				}
			}
		}
		
	}
	
	public static ArrayList<Integer> hashMapTrackingNumber(ArrayList<Order> shoes) {
		
		ArrayList<Integer> hashMap = new ArrayList<>();
		int value = 0;
		int key = 0;
		
		for(int i = 0; i < shoes.size(); i++ ) {
			
			
			
		}
		
		
	}
	
	 public static void trackOrder() {
		
	}
	 
	 public static String generateTrackingNumber() {
		 
		 Random r = new Random();
		 String trackNum = "";
		 
		 for(int i = 0; i < 10; i++) {
			 int num = r.nextInt(10);
			 trackNum += num;
		 }
		 
		 return trackNum;
		 
	 }
	 
	 public static boolean checkInStock(Lace l, Sole s, Body b) {
		 
		 boolean lCheck = false;
		 boolean sCheck = false;
		 boolean bCheck = false;
		 
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i) instanceof Lace) {
				Lace test = (Lace)parts.get(i);
				if (l.getColor().equals(test.getColor()) && l.getLength() == test.getLength()) {
					lCheck = true;
				}
			}  else if (parts.get(i) instanceof Sole) {
				Sole test = (Sole)parts.get(i);
				if (s.getColor().equals(test.getColor()) && s.getSize() == test.getSize() && s.getPattern().equals(test.getPattern())) {
					sCheck = true;
				}
			}  else if (parts.get(i) instanceof Body) {
				Body test = (Body)parts.get(i);
				if (b.getColor().equals(test.getColor()) && b.getSize() == test.getSize() && b.getPattern().equals(test.getPattern()) && b.getMaterial().equals(test.getMaterial())) {
					bCheck = true;
				}
			}
		}
		
		if (lCheck == true && sCheck == true && bCheck == true) {
			return true;
		}  else {
			return false;
		}
		 
	 }
	
	public static void resupply(Lace l, Sole s, Body b) {
		parts.add(l);
		parts.add(s);
		parts.add(b);		
	}
	

	
	
	
	
}
