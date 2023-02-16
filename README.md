#Description
Weekly updated Spotify Top Global Charts using AWS Lamda, Cloudwatch schedule, Java, JGit, and Spotify Api

#How it works
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
