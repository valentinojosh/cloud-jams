import org.apache.hc.core5.http.ParseException;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Foo {
    private static final String clientId = "Foo";
    private static final String clientSecret = "Bar";
    private static final String playlistId = "Baz";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();

    public static void main(String[] args) throws IOException, ParseException, SpotifyWebApiException, GitAPIException {
        //Method that connects to spotify api and retrieves data that will be added in to the file
        Paging<PlaylistTrack> playlistTrackPaging = getSpotifyData();

        //Method that checks if the absolute path to the local repo is present and deletes it if so
        //This is because when cloning the repository it will throw an error if it is already present locally
        checkLocalRepo();

        // Method to Clone the GitHub repository to a local repo
        cloneRemoteRepo();

        // Method to update the file and write it passing the spotify data
        writeUpdatedFile(playlistTrackPaging);

        // Method to commit the changes to the GitHub repo
        commitRemoteRepo();

        // Method to push the committed changes to GitHub Repo
        pushRemoteRepo();
    }

    private static void pushRemoteRepo() throws IOException, GitAPIException {
        File relativeDir = new File("SpotifyTopCharts");
        File absoluteDir = new File(relativeDir.getAbsolutePath());
        Git git = Git.open(absoluteDir);
        PushCommand push = git.push();
        push.setCredentialsProvider(new UsernamePasswordCredentialsProvider("Foo", "Bar"));
        push.setPushAll();
        push.call();
    }

    private static void commitRemoteRepo() throws GitAPIException, IOException {
        File relativeDir = new File("SpotifyTopCharts");
        File absoluteDir = new File(relativeDir.getAbsolutePath());
        Git git = Git.open(absoluteDir);
        git.add().addFilepattern("WeeklyCharts/Charts.txt").setUpdate(true).call();
        CommitCommand commit = git.commit();
        commit.setAuthor("Josh Valentino", "valentinojosh03@gmail.com");
        commit.setCommitter("Josh Valentino", "valentinojosh03@gmail.com");
        commit.setMessage("Weekly AWS Lambda Update");
        commit.call();
    }

    private static void writeUpdatedFile(Paging<PlaylistTrack> playlistTrackPaging) throws IOException {
        Path localFilePath = Paths.get("SpotifyTopCharts/WeeklyCharts/Charts.txt");
        String oldContent = Files.readString(localFilePath);
        int weekNum = 0;
        String[] lines = oldContent.split("\n");

        for (String line : lines) {
            if (line.contains("Week")) {
                weekNum = (Integer.parseInt(line.substring(4).replaceAll("\\s+", ""))+1);
                break;
            }
        }

        StringBuilder newContent = new StringBuilder("Week ");
        newContent.append(weekNum).append("\n").append("Top Tracks:");
        for(int i = 0; i < 5; i++){
            Track track = (Track) playlistTrackPaging.getItems()[i].getTrack();
            newContent.append("\n").append(i + 1).append(": ").append(track.getName()).append(" - ");

            ArtistSimplified[] artists = track.getArtists();
            for (int j = 0; j < artists.length; j++) {
                if (j > 0) {
                    newContent.append(", ");
                }
                newContent.append(artists[j].getName());
            }
        }
        String updatedContent = newContent + "\n\n" + oldContent;
        Files.writeString(localFilePath, updatedContent);
    }

    private static void cloneRemoteRepo() throws GitAPIException {
        Git git = Git.cloneRepository()
                .setURI("https://github.com/valentinojosh/SpotifyTopCharts")
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("Foo", "Bar"))
                .call();
    }

    private static void checkLocalRepo() {
        File relativeDir = new File("SpotifyTopCharts");
        File absoluteDir = new File(relativeDir.getAbsolutePath());
        if (absoluteDir.exists()) {
            deleteDirectory(absoluteDir);
        }
    }

    private static Paging<PlaylistTrack> getSpotifyData() throws IOException, ParseException, SpotifyWebApiException, GitAPIException{
        Paging<PlaylistTrack> playlistTrackPaging = null;
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        ClientCredentials clientCredentials = clientCredentialsRequest.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                .getPlaylistsItems(playlistId)
                .limit(5)
                .build();

        try {
            playlistTrackPaging = getPlaylistsItemsRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return playlistTrackPaging;
    }

    public static boolean deleteDirectory(File directory) {
        if (!directory.exists()) {
            return true;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }
}
