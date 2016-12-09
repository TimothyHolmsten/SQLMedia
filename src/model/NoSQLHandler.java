package model;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import objectmodels.*;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-11-29.
 */
public class NoSQLHandler implements Query {

    private MongoDatabase database;
    //private  Document document = null;


    public NoSQLHandler() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("lab2_database");
    }

    @Override
    public int addDirector(String directorName) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Director");
        Document document = new Document("name", directorName);

        collection.insertOne(document);

        return 0;
    }

    @Override
    public void addMovie(String title, String genre, Director director, User user) throws QueryException {

        MongoCollection<Document> collection = database.getCollection("Movie");

        Document document = new Document("title", title).append("genre", genre);
        document.append("director", new Document("name", director.getName()));
        document.append("addBy", new Document("name", user.getName()));
        collection.insertOne(document);
    }

    @Override
    public void addAlbum(String title, ArrayList<Song> songs, User user) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Album");
        ArrayList<Document> songlist = new ArrayList<>();


        Document document = new Document("title", title).append("genre", songs.get(0).getGenre());
        document.append("addBy", new Document("name", user.getName()));


        for (Song song : songs) {
            ArrayList<Document> songArtists = new ArrayList<>();
            for (Artist artist : song.getArtists())
                songArtists.add(new Document("name", artist.getName())
                        .append("addBy", new Document("name", artist.getAddedByUser().getName())));

            songlist.add(new Document("title", song.getTitle())
                    .append("genre", song.getGenre())
                    .append("artists", songArtists)
                    .append("addBy", user.getName()));

        }
        document.append("songs", songlist);
        collection.insertOne(document);
    }

    @Override
    public void addSong(String title, String genre, User user, Artist artist) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Song");

        Document artistDoc = new Document("name", artist.getName())
                .append("addBy", new Document("name", artist.getAddedByUser().getName()));

        ArrayList artistList = new ArrayList();
        artistList.add(artistDoc);

        Document document = new Document("title", title).append("genre", genre);
        document.append("addBy", new Document("name", user.getName()))
                .append("artists", artistList);

        collection.insertOne(document);
    }

    @Override
    public void addSong(String title, String genre, User user, String artistName) throws QueryException {

        Document artist = new Document("name", artistName).append("addBy", new Document("name", user.getName()));

        MongoCollection<Document> artistCollection = database.getCollection("Artist");
        artistCollection.insertOne(artist);

        MongoCollection<Document> SongCollection = database.getCollection("Song");

        ArrayList artistList = new ArrayList();
        artistList.add(artist);

        Document document = new Document("title", title).append("genre", genre);
        document.append("artists", artistList);
        document.append("addBy", new Document("name", user.getName()));

        SongCollection.insertOne(document);
    }

    @Override
    public void addArtistToSong(Artist artist, Song song, User user) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Song");


    }

    @Override
    public int addMedia(String title, String genre, User user) throws QueryException {


        return 0;
    }

    @Override
    public void addUser(String userName) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("User");
        Document document = new Document("userName", userName);
        collection.insertOne(document);

    }

    @Override
    public void addReview(String reviewText, int rating, User user, int mediaId) throws QueryException {

    }

    @Override
    public Movie getMovieById(int id) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Movie");
        MongoCursor<Document> cursor = collection.find(Filters.eq("_id", id)).iterator();
        return getMoviesCursor(cursor).get(0);
    }

    @Override
    public Director getDirectorById(int id) throws QueryException {
        return null;
    }

    @Override
    public Artist getArtistById(int id) throws QueryException {
        return null;
    }

    @Override
    public Song getSongById(int id) throws QueryException {
        return null;
    }

    @Override
    public User getUserById(int id) throws QueryException {
        return null;
    }

    @Override
    public Album getAlbumById(int id) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Director> getDirectors() throws QueryException {
        ArrayList<Director> directors = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("Director");
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            directors.add(new Director(doc.getString("name")));
        }
        return directors;
    }

    @Override
    public ArrayList<Artist> getSongArtists(int songId) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Artist> getArtists() throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Media> getAllMedia() throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Song> getSongs() throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Song");
        MongoCursor<Document> cursor = collection.find().iterator();

        return getSongsCursor(cursor);
    }

    @Override
    public ArrayList<Movie> getMovies() throws QueryException {
        ArrayList<Movie> movies = new ArrayList<>();

        MongoCollection<Document> collection = database.getCollection("Movie");

        MongoCursor<Document> cursor = collection.find().iterator();

        return getMoviesCursor(cursor);
    }

    @Override
    public ArrayList<Album> getAlbums() throws QueryException {
        return null;
    }

    private ArrayList<Movie> getMoviesCursor(MongoCursor<Document> cursor) {
        ArrayList<Movie> movies = new ArrayList<>();

        while (cursor.hasNext()) {
            Document obj = cursor.next();

            Document subObj = (Document) obj.get("director");
            Director director = new Director(subObj.getString("name"));

            subObj = (Document) obj.get("user");
            User user = new User(subObj.getString("userName"));

            Movie movie = new Movie(obj.getString("title"), obj.getString("genre"), director, user);
            movies.add(movie);
        }
        cursor.close();
        return movies;
    }

    private ArrayList<Album> getAlbumsCursor(MongoCursor<Document> cursor) {
        ArrayList<Album> albums = new ArrayList<>();

        while (cursor.hasNext()) {
            Document obj = cursor.next();


            Document subObj = (Document) obj.get("addBy");
            User user = new User(subObj.getString("name"));

            ArrayList<Document> songsDoc = (ArrayList<Document>) obj.get("songs");
            ArrayList<Song> songs = new ArrayList<>();

            for (Document doc : songsDoc) {
                ArrayList<Artist> artists = new ArrayList<>();
                for (Document artist : ((ArrayList<Document>) doc.get("artists")))
                    artists.add(new Artist(artist.getString("name"), new User(artist.getString("addBy.name"))));
                songs.add(new Song(
                        doc.getString("title"),
                        doc.getString("genre"),
                        user,
                        artists
                ));
            }

            Album album = new Album(obj.getString("title"), obj.getString("genre"), user, songs);
            albums.add(album);
        }
        cursor.close();
        return albums;
    }

    private ArrayList<Song> getSongsCursor(MongoCursor<Document> cursor) {
        ArrayList<Song> songs = new ArrayList<>();

        while (cursor.hasNext()) {
            Document obj = cursor.next();

            Document subObj = (Document) obj.get("addBy");
            User user = new User(subObj.getString("name"));

            ArrayList<Document> artists = (ArrayList<Document>) obj.get("artists");
            ArrayList<Artist> songArtists = new ArrayList<>();

            for (Document doc : artists)
                songArtists.add(new Artist(doc.getString("name"), new User(((Document) doc.get("addBy")).getString("name"))));

            Song song = new Song(obj.getString("title"), obj.getString("genre"), user, songArtists);
            songs.add(song);
        }
        cursor.close();
        return songs;
    }

    private ArrayList<Artist> getArtistsCursor(MongoCursor<Document> cursor) {
        ArrayList<Artist> artists = new ArrayList<>();

        while (cursor.hasNext()) {
            Document obj = cursor.next();

            Artist artist = new Artist(obj.getString("name"), new User(obj.getString("addBy.name")));
            artists.add(artist);
        }
        cursor.close();
        return artists;
    }

    @Override
    public ArrayList<Movie> getMoviesByTitle(String title) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Movie");
        MongoCursor<Document> cursor = collection.find(Filters.eq("title", title)).iterator();
        return getMoviesCursor(cursor);
    }

    @Override
    public ArrayList<Song> getSongsByTitle(String title) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Song");
        MongoCursor<Document> cursor = collection.find(Filters.eq("title", title)).iterator();
        return getSongsCursor(cursor);
    }

    @Override
    public ArrayList<Album> getAlbumsByTitle(String title) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Album");
        MongoCursor<Document> cursor = collection.find(Filters.eq("title", title)).iterator();
        return getAlbumsCursor(cursor);
    }

    @Override
    public ArrayList<Movie> getMoviesByGenre(String genre) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Movie");
        MongoCursor<Document> cursor = collection.find(Filters.eq("genre", genre)).iterator();
        return getMoviesCursor(cursor);
    }

    @Override
    public ArrayList<Song> getSongsByGenre(String genre) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Song");
        MongoCursor<Document> cursor = collection.find(Filters.eq("genre", genre)).iterator();
        return getSongsCursor(cursor);
    }

    @Override
    public ArrayList<Album> getAlbumsByGenre(String genre) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Album");
        MongoCursor<Document> cursor = collection.find(Filters.eq("genre", genre)).iterator();
        return getAlbumsCursor(cursor);
    }

    @Override
    public ArrayList<Movie> getMoviesByDirector(String directorName) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Movie");
        MongoCursor<Document> cursor = collection.find(Filters.eq("director.name", directorName)).iterator();
        return getMoviesCursor(cursor);
    }

    @Override
    public ArrayList<Song> getSongsByArtist(String artistName) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Song");
        MongoCursor<Document> cursor = collection.find(Filters.eq("artists.name", artistName)).iterator();
        return getSongsCursor(cursor);
    }

    @Override
    public ArrayList<Album> getAlbumsByArtist(String artistName) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Album");
        MongoCursor<Document> cursor = collection.find(Filters.eq("songs.artists.name", artistName)).iterator();
        return getAlbumsCursor(cursor);
    }

    @Override
    public ArrayList<Song> getSongsByRating(int rating) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Song");
        MongoCursor<Document> cursor = collection.find(Filters.eq("rating", rating)).iterator();
        return getSongsCursor(cursor);
    }

    @Override
    public ArrayList<Movie> getMoviesByRating(int rating) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("Movie");
        MongoCursor<Document> cursor = collection.find(Filters.eq("rating", rating)).iterator();
        return getMoviesCursor(cursor);
    }

    @Override
    public User getUserByName(String userName) throws QueryException {
        MongoCollection<Document> collection = database.getCollection("User");
        Document user = (collection.find(Filters.eq("userName", userName)).first());
        if (user == null) {
            throw new QueryException("No user found");
        }
        return new User(user.getString("userName"));
    }
}
