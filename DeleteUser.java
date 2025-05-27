package mybanksystem;

import java.io.*;

public class DeleteUser {

    private static final String FILE_NAME = "bank.txt"; // your file with cardno,pin lines

    public static boolean deleteUserByCardOrPin(String userId) {
    boolean found = false;

    File inputFile = new File(FILE_NAME);
    File tempFile = new File("temp_" + FILE_NAME);

    try (
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    ) {
        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println("Reading line: " + line); // Debug

            String[] parts = line.split(",");
            if (parts.length < 2) {
                System.out.println("Skipping malformed line");
                writer.write(line + System.lineSeparator());
                continue;
            }

            String cardNo = parts[0].trim();
            String pin = parts[1].trim();

            if (userId.equals(cardNo) || userId.equals(pin)) {
                System.out.println("Found user to delete: " + line);
                found = true;
                // skip writing this line to delete it
                continue;
            }

            writer.write(line + System.lineSeparator());
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }

    if (found) {
        if (!inputFile.delete()) {
            System.out.println("Could not delete original file");
            return false;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename temp file");
            return false;
        }
        System.out.println("User deleted successfully.");
    } else {
        System.out.println("User not found, no changes made.");
        tempFile.delete(); // delete temp file if no deletion
    }

    return found;
}

}
