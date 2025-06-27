import java.io.*;
import java.util.Scanner;

public class NotesApp {

    static final String FILE_NAME = "notes.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n--- Notes App ---");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Delete Note");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addNote(scanner);
                    break;
                case 2:
                    viewNotes();
                    break;
                case 3:
                    deleteNote(scanner);
                    break;
                case 4:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }

        } while (choice != 4);

        scanner.close();
    }

    public static void addNote(Scanner scanner) {
        try {
            System.out.print("Enter your note: ");
            String note = scanner.nextLine();

            FileWriter writer = new FileWriter(FILE_NAME, true); // append mode
            writer.write(note + "\n");
            writer.close();

            System.out.println("Note added successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the note.");
        }
    }

    public static void viewNotes() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists() || file.length() == 0) {
                System.out.println("No notes found.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            System.out.println("\n--- Your Notes ---");
            while ((line = reader.readLine()) != null) {
                System.out.println("- " + line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the notes.");
        }
    }

    public static void deleteNote(Scanner scanner) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists() || file.length() == 0) {
                System.out.println("No notes found to delete.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            java.util.List<String> notes = new java.util.ArrayList<>();
            while ((line = reader.readLine()) != null) {
                notes.add(line);
            }
            reader.close();

            if (notes.isEmpty()) {
                System.out.println("No notes to delete.");
                return;
            }

            System.out.println("\n--- Notes List ---");
            for (int i = 0; i < notes.size(); i++) {
                System.out.println((i + 1) + ". " + notes.get(i));
            }

            System.out.print("Enter the note number to delete: ");
            int deleteIndex;
            try {
                deleteIndex = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
                return;
            }

            if (deleteIndex < 0 || deleteIndex >= notes.size()) {
                System.out.println("Invalid note number.");
                return;
            }

            notes.remove(deleteIndex);

            FileWriter writer = new FileWriter(file);
            for (String note : notes) {
                writer.write(note + "\n");
            }
            writer.close();

            System.out.println("Note deleted successfully!");

        } catch (IOException e) {
            System.out.println("An error occurred while deleting the note.");
        }
    }
}
