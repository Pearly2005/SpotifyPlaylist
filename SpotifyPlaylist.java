import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class SpotifyPlaylist {
    private static ArrayList<String> playlist = new ArrayList<>();
    private static Stack<ArrayList<String>> undoStack = new Stack<>();
    private static Stack<ArrayList<String>> redoStack = new Stack<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n==== Spotify Playlist ====");
            System.out.println("1. Add song");
            System.out.println("2. Remove last song");
            System.out.println("3. Undo");
            System.out.println("4. Redo");
            System.out.println("5. View playlist");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter song name: ");
                    String song = scanner.nextLine();
                    pushToUndo();
                    redoStack.clear();
                    playlist.add(song);
                    System.out.println("Song added!");
                    break;
                case 2:
                    if (playlist.isEmpty()) {
                        System.out.println("Playlist is already empty.");
                    } else {
                        pushToUndo();
                        redoStack.clear();
                        String removed = playlist.remove(playlist.size() - 1);
                        System.out.println("Removed: " + removed);
                    }
                    break;
                case 3:
                    if (undoStack.isEmpty()) {
                        System.out.println("Nothing to undo.");
                    } else {
                        pushToRedo();
                        playlist = undoStack.pop();
                        System.out.println("Undo performed.");
                    }
                    break;
                case 4:
                    if (redoStack.isEmpty()) {
                        System.out.println("Nothing to redo.");
                    } else {
                        pushToUndo();
                        playlist = redoStack.pop();
                        System.out.println("Redo performed.");
                    }
                    break;
                case 5:
                    viewPlaylist();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        scanner.close();
    }

    // Push a deep copy of the current playlist to the undo stack
    private static void pushToUndo() {
        undoStack.push(new ArrayList<>(playlist));
    }

    // Push a deep copy of the current playlist to the redo stack
    private static void pushToRedo() {
        redoStack.push(new ArrayList<>(playlist));
    }

    // Display current playlist
    private static void viewPlaylist() {
        System.out.println("\n--- Current Playlist ---");
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
        } else {
            for (int i = 0; i < playlist.size(); i++) {
                System.out.println((i + 1) + ". " + playlist.get(i));
            }
        }
    }
}
