
package mybanksystem;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Conn {
    private static final String FILE_PATH = "signup2_data.txt";

    public void saveSignupTwoData(String formno, String religion, String category, String income,
                                  String education, String occupation, String pan, String aadhar,
                                  String scitizen, String eaccount) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true));
            writer.write("Form No: " + formno + "\nReligion: " + religion +
                         "\nCategory: " + category + "\nIncome: " + income +
                         "\nEducation: " + education + "\nOccupation: " + occupation +
                         "\nPAN: " + pan + "\nAadhar: " + aadhar +
                         "\nSenior Citizen: " + scitizen + "\nExisting Account: " + eaccount + "\n\n");
            writer.close();
            System.out.println("Data saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error while saving data: " + e.getMessage());
        }
    }
}
