import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main{
    static Scanner input= new Scanner(System.in);
    static double totalPrice=0;
    static String currentUser = "";
    
    static ArrayList<String> username = new ArrayList<String>();
    static ArrayList<String> password = new ArrayList<String>();
    
    static ArrayList<String> prodList = new ArrayList<String>();
    static ArrayList<Double> priceList = new ArrayList<Double>();
    static ArrayList<Integer> quanList = new ArrayList<Integer>();
    static int choice= 0;
    
    private static void signingUp(ArrayList username, ArrayList password){
    String name;
    String pass;

    while (true){
        try {
            System.out.print("Input username: ");
            name = input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input.");
            input.nextLine();
            continue;
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            System.out.println("Username must contain 5-15 alphanumeric characters. Try another username.");
            continue;
        }

        while (true) {
            try {
                System.out.print("Enter password (8-20 characters, must contain at least one uppercase letter & one number): ");
                pass = input.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input.");
                input.nextLine();
                continue;
            }

            Pattern pattern2 = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$");
            Matcher matcher2 = pattern2.matcher(pass);
            if (!matcher2.matches()) {
                System.out.println("Invalid password. Must be 8-20 characters long, contain at least one uppercase letter & one number.");
                continue;
            }

            username.add(name);
            currentUser = name;

            password.add(pass);
            System.out.println("\nSignup successful!\n");
            prodList.clear();
            priceList.clear();
            quanList.clear();
            break;
        }
        break;
    }
}

    
    
    private static boolean logIn(){
    System.out.println("Welcome! Please log-in before using the Cash Register!\n");
    System.out.println("List of usernames: " + username);

    while (true) {
        String user = "";
        try {
            System.out.print("\nEnter username: ");
            user = input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input.");
            input.nextLine();
            continue;
        }

        boolean userFound = false;
        for (int i = 0; i < username.size(); i++) {
            if (username.get(i).equals(user)) {
                userFound = true;
                while (true) {
                    String PW = "";
                    try {
                        System.out.print("Enter password: ");
                        PW = input.nextLine();
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                        input.nextLine();
                        continue;
                    }

                    if (password.get(i).equals(PW)) {
                        System.out.println("\nLogged in successfully!\n");
                        currentUser = user;
                        display();
                        System.out.print("\n");
                        return true;
                    } else {
                        System.out.println("\nIncorrect password. Please try again.\n");
                    }
                }
            }
        }
        if (!userFound) {
            System.out.println("\nIncorrect username. Please try again.");
        }
    }
}

        
    public static void LogTrans(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter petsa = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			writer.write("Date & Time: " + now.format(petsa));
			writer.newLine();
			
			writer.write("User: " + currentUser);
			writer.newLine();
			
			writer.write("Purchased items:");
			writer.newLine();
			for(int i=0; i<prodList.size(); i++){
			    writer.write(" > " + prodList.get(i) + " - Qty: " + quanList.get(i) + " - Price: " + priceList.get(i));
			    writer.newLine();
			}
			
			writer.write("Total Price: " + totalPrice);
			writer.newLine();
			writer.write("-----------------------------------------------------");
			writer.newLine();
			
		} catch (Exception e) {
		    System.out.println("Error saving the Transaction log.");
		}
    }
    
    
    public static void DispTrans(){
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            System.out.println("\n---------- Transaction Logs ---------- ");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("----------  End of Logs ---------- \n");
        } catch (Exception e) {
            System.out.println("No transaction logs found // error reading the log.");
        }
    }
    

    private static void mainMenu(){
    System.out.println("----------------------------------");
    System.out.println("Welcome to the Cash Register!");
    System.out.println("----------------------------------");
    boolean isRunning = true;
    while (isRunning) {
        int choice = 0;
        while (true) {
            try {
                System.out.print("\nWhat would you like to do?\n(1-Add, 2-Remove, 3-Edit, 4-Payment, 5-Transactions, 6-Log-out): ");
                choice = input.nextInt();
                input.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
        System.out.print("\n");
        switch (choice) {
            case 1:
                addProduct(prodList);
                break;
            case 2:
                remove();
                display();
                break;
            case 3:
                edit();
                break;
            case 4:
                pay();
                break;
            case 5:
                DispTrans();
                break;
            case 6:
                homePage();
                break;
            default:
                break;
        }
    }
}

    
    public static void addProduct(ArrayList prodList){
    boolean continueLoop = true;
    while (continueLoop) {
        String enterProd = "";
        try {
            System.out.print("Enter product: ");
            enterProd = input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input.");
            input.nextLine();
            continue;
        }
        prodList.add(enterProd);

        double enterPrice = 0;
        while (true) {
            try {
                System.out.print("Enter price: ");
                enterPrice = input.nextDouble();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
        priceList.add(enterPrice);

        int enterQuan = 0;
        while (true) {
            try {
                System.out.print("Enter quantity: ");
                enterQuan = input.nextInt();
                input.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
        quanList.add(enterQuan);

        System.out.println("Items successfully inputted!\n");

        String fin = "";
        try {
            System.out.print("Continue purchasing? (y/n): ");
            fin = input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input.");
            input.nextLine();
        }
        if (fin.equalsIgnoreCase("n")) {
            continueLoop = false;
            display();
            break;
        }
    }
}

    
    public static void edit(){
        boolean trueEdit= true;
        while (trueEdit){
        display();
        System.out.print("\n");
        int ans=-1;
        while (true){
        System.out.print("What would you like to edit? (1-Product, 2-Quantity): ");
        try{
            ans= input.nextInt();
            input.nextLine();
            System.out.print("\n");
            
            if (ans<1 || ans>3){
                System.out.print("Please try again.");
                continue;
            }
            break;
            
        } catch (Exception e) {
            System.out.print("Invalid input. Please enter a number");
            System.out.print("\n");
            input.nextLine();
        }
        }
            
            switch(ans){
                case 1:
                        int index=-1;
                        while (true){
                        System.out.print("Enter the product to edit: ");
                        String pEdit= input.nextLine();
                        
                        
                        for (int i = 0; i < prodList.size(); i++) {
                            if (prodList.get(i).equalsIgnoreCase(pEdit)) {
                                index = i;
                                break;
                            }
                        }

                        if (index == -1) {
                        System.out.println("Product not found.");
                        continue;
                        } else{
                            break;
                        }
                        }
                        
            
                        try{
                            System.out.print("Enter new product name for " + prodList.get(index) + ": ");
                            String newProd= input.nextLine();
                            
                            prodList.set(index, newProd);
                            System.out.print("Product successfully updated for " + prodList.get(index) + "!");
                            System.out.print("\n");
                        
                            display();
                            
                            
                        } catch (Exception e){
                            System.out.print("Invalid input.");
                            input.nextLine();
                        }
                        System.out.print("\n");
                        System.out.print("Would you like to continue editing? (y/n): ");
                        try{
                            String conEd= input.nextLine();
                            if (conEd.equalsIgnoreCase("n")){
                                trueEdit= false;
                                mainMenu();
                                break;
                            }
                        } catch (Exception e){
                            System.out.print("Invalid input. Enter y or n.");
                        }
                        
                        
                        break;
                    case 2:
                        int ind=-1;
                        while (true){
                        System.out.print("Enter the product you'd like to edit the quantity of: ");
                        String qEdit= input.nextLine();
                        
                        
                        for (int i = 0; i < prodList.size(); i++) {
                            if (prodList.get(i).equalsIgnoreCase(qEdit)) {
                                ind = i;
                                break;
                            }
                        }

                        if (ind == -1) {
                        System.out.println("Product not found.");
                        continue;
                        } else{
                            break;
                        }
                        }
                        
            
                        try{
                            System.out.print("Enter new quantity for " + prodList.get(ind) + ": ");
                            int newQuan= input.nextInt();
                            input.nextLine();
                            
                            quanList.set(ind, newQuan);
                            System.out.print("Product quantity successfully updated for " + prodList.get(ind) + "!");
                            System.out.print("\n");
                        
                            display();
                            
                        } catch (Exception e){
                            System.out.print("Invalid input.");
                            input.nextLine();
                        }
                        
                        System.out.print("\n");
                        System.out.print("Would you like to continue editing? (y/n): ");
                        String conEd= input.nextLine();
                        try{
                            if (conEd.equalsIgnoreCase("n")){
                                trueEdit= false;
                                mainMenu();
                                break;
                            }
                        } catch (Exception e){
                            System.out.print("Invalid input. Enter y or n.");
                        }
                        break;
                    default:
                        break;
                    
                
                    

            }
            
        
        }
        
    }
    
    public static double display(){
        System.out.println("\n-------------Your Purchases------------");
        System.out.println("Products" + "\t" + "Quantity" + "\t" + "Price" + "\t" + "Total");
        double totalPrice=0;
        for(int i=0; i<prodList.size(); i++){
            double total= quanList.get(i)*priceList.get(i);
            totalPrice+=total;
            System.out.println(prodList.get(i) + "\t" + "\t" + quanList.get(i) + "\t" + "\t" + priceList.get(i) + "\t" + total);
        }
            System.out.println("----------------------------");
            System.out.print("Total price: " + totalPrice);
            System.out.print("\n");
            return totalPrice;
    }
    
    public static void pay(){
        totalPrice= display();
        System.out.print("\n");
        System.out.print("Enter payment: ");
        double payment= input.nextDouble();
        
        double change= payment-totalPrice;
        
        if (payment>totalPrice){
            System.out.println("\n--------------------------------");
            System.out.print("Your change is: " + change);
            System.out.print("\n");
            System.out.println("--------------------------------");
            System.out.print("Thank you for purchasing!\n");
            LogTrans();
        } else {
            System.out.print("Invalid payment");
        }
        
    }
    
    public static void remove() {
    while (true){
    try {
        System.out.print("Enter the name of the product to remove: ");
        String productName = input.nextLine();
        int index = prodList.indexOf(productName);
        
        if (index != -1) {
            prodList.remove(index);
            priceList.remove(index);
            quanList.remove(index);
            System.out.println("Product removed successfully!\n");
            break;
        } else {
            System.out.println("Product not found.");
            
        }
        
    } catch (Exception e) {
        System.out.println("Invalid input.");
        input.nextLine();
    }
    }
}

    private static void homePage() {
    System.out.println("----------------------------------");
    System.out.println("Welcome to the Home Page!");
    System.out.println("----------------------------------");
    boolean isTrue = true;
    while (isTrue) {
        int pick = 0;
        while (true) {
            try {
                System.out.print("\nWould you like to signup, login, or exit?\n(1-Signup, 2-Login, 3-Exit): ");
                pick = input.nextInt();
                input.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
        System.out.print("\n");
        switch (pick) {
            case 1:
                signingUp(username, password);
                while (!logIn()) {
                    System.out.println("Login failed. Please try again.");
                }
                isTrue = false;
                break;
            case 2:
                logIn();
                isTrue = false;
                break;
            case 3:
                try {
                    System.out.print("Are you sure you want to exit? Data will be cleared when exiting (y/n): ");
                    String confirm = input.nextLine();
                    if (confirm.equalsIgnoreCase("y")) {
                        username.clear();
                        password.clear();
                        prodList.clear();
                        priceList.clear();
                        quanList.clear();
                        System.out.println("\nData cleared. Thank you for using the cash register!");
                        System.exit(0);
                    } else {
                        System.out.println("Returning to home page!");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                    input.nextLine();
                }
                break;
            default:
                break;
        }
    }
}


    
    
    public static void main(String[] args){
        
        homePage();
        mainMenu();
        
        
        
        


        
    }
}