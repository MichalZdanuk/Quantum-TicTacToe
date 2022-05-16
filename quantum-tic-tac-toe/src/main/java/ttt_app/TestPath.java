package ttt_app;

public class TestPath {
    public static void main(String[] args) {
        printCurrentWorkingDirectory();
    }

    private static void printCurrentWorkingDirectory() {
        String userDirectory = System.getProperty("user.dir") + "\\sounds\\clickSound.wav";
        System.out.println(userDirectory);
    }
}
