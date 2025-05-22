package com.example;

	import java.io.*;
	import java.time.LocalDate;
	import java.time.YearMonth;
	import java.util.*;

	class Transaction {
	    LocalDate date;
	    String type;
	    String category;
	    double amount;

	    public Transaction(LocalDate date, String type, String category, double amount) {
	        this.date = date;
	        this.type = type;
	        this.category = category;
	        this.amount = amount;
	    }

	    @Override
	    public String toString() {
	        return date + "," + type + "," + category + "," + amount;
	    }
	}

	public class Tracker {
	    private List<Transaction> transactions = new ArrayList<>();
	    private Scanner scanner = new Scanner(System.in);

	    public void addTransaction() {
	        System.out.print("Enter date (yyyy-mm-dd): ");
	        LocalDate date = LocalDate.parse(scanner.nextLine());

	        System.out.print("Type (Income/Expense): ");
	        String type = scanner.nextLine();

	        System.out.print("Category (e.g., Salary/Business or Food/Rent/Travel): ");
	        String category = scanner.nextLine();

	        System.out.print("Amount: ");
	        double amount = Double.parseDouble(scanner.nextLine());

	        transactions.add(new Transaction(date, type, category, amount));
	    }

	    public void viewMonthlySummary() {
	        Map<String, Double> income = new HashMap<>();
	        Map<String, Double> expense = new HashMap<>();
	        double totalIncome = 0, totalExpense = 0;

	        System.out.print("Enter month and year (yyyy-mm): ");
	        YearMonth ym = YearMonth.parse(scanner.nextLine());

	        for (Transaction t : transactions) {
	            if (YearMonth.from(t.date).equals(ym)) {
	                if (t.type.equalsIgnoreCase("Income")) {
	                    income.put(t.category, income.getOrDefault(t.category, 0.0) + t.amount);
	                    totalIncome += t.amount;
	                } else {
	                    expense.put(t.category, expense.getOrDefault(t.category, 0.0) + t.amount);
	                    totalExpense += t.amount;
	                }
	            }
	        }

	        System.out.println("\n--- Monthly Summary ---");
	        System.out.println("Income:");
	        income.forEach((k, v) -> System.out.println(k + ": " + v));
	        System.out.println("Total Income: " + totalIncome);

	        System.out.println("\nExpenses:");
	        expense.forEach((k, v) -> System.out.println(k + ": " + v));
	        System.out.println("Total Expense: " + totalExpense);
	        System.out.println("Net Savings: " + (totalIncome - totalExpense));
	    }

	    public void saveToFile(String filename) throws IOException {
	        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
	            for (Transaction t : transactions) {
	                writer.println(t);
	            }
	        }
	    }

	    public void loadFromFile(String filename) throws IOException {
	        transactions.clear();
	        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(",");
	                Transaction t = new Transaction(
	                    LocalDate.parse(parts[0]),
	                    parts[1],
	                    parts[2],
	                    Double.parseDouble(parts[3])
	                );
	                transactions.add(t);
	            }
	        }
	    }

	    public static void main(String[] args) throws IOException {
	        Tracker tracker = new Tracker();
	        Scanner scanner = new Scanner(System.in);
	        while (true) {
	            System.out.println("\n1. Add Transaction\n2. View Monthly Summary\n3. Load From File\n4. Save To File\n5. Exit");
	            System.out.print("Choice: ");
	            int choice = Integer.parseInt(scanner.nextLine());

	            switch (choice) {
	                case 1 -> tracker.addTransaction();
	                case 2 -> tracker.viewMonthlySummary();
	                case 3 -> {
	                    System.out.print("Enter filename: ");
	                    tracker.loadFromFile(scanner.nextLine());
	                }
	                case 4 -> {
	                    System.out.print("Enter filename: ");
	                    tracker.saveToFile(scanner.nextLine());
	                }
	                case 5 -> {
	                    System.out.println("Exiting...");
	                    return;
	                }
	                default -> System.out.println("Invalid choice");
	            }
	        }
	    }
	}




    

