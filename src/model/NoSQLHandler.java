package model;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import objectmodels.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by timothy on 2016-11-29.
 */
public class NoSQLHandler implements Query {

    private MongoDatabase database = null;
    //private  Document document = null;


    public NoSQLHandler(){
        MongoClient mongoClient = new MongoClient("localhost", 27017);
       database= mongoClient.getDatabase("lab2_database");


    }
    @Override
    public int addDirector(String directorName) throws QueryException {
        MongoCollection collection= database.getCollection("Director");
        Document document= new Document("name",directorName);

        collection.insertOne(document);



        return 0;
    }

    @Override
    public void addMovie(String title, String genre, Director director, User user) throws QueryException {

        MongoCollection collection= database.getCollection("Movie");
        addDirector(director.getName());
        addUser(user.getName());

        Document document= new Document("title",title).append("genre",genre);
        document.append("director", new Document("name", director.getName()));
        document.append("user", new Document("name",user.getName()));
        collection.insertOne(document);

    }

    @Override
    public void addAlbum(String title, ArrayList<Song> songs, User user) throws QueryException {
        MongoCollection collection= database.getCollection("Album");
        ArrayList<Document> songlist= new ArrayList<Document>();


        Document document = new Document("title",title);
        document.append("user",new Document("name",user.getName()));


            for (Song song:songs) {
                songlist.add(new Document("title", song.getTitle())
                                            .append("genre",song.getGenre())
                                            .append("addBy",user.getName()));

            }
            document.append("songList",songlist);
            collection.insertOne(document);
    }

    @Override
    public void addSong(String title, String genre, User user, Artist artist) throws QueryException {
        ArrayList<Document> artists = new ArrayList<Document>();
        artists.add(new Document("name",artist.getName()).append("addby",artist.getAddedByUserId()));

        MongoCollection SongCollection=database.getCollection("Song");


        Document document = new Document("title",title).append("genre",genre);
        document.append("artist",artists);
        document.append("addBy",new Document("name",user.getName()));

        SongCollection.insertOne(document);

    }

    @Override
    public void addSong(String title, String genre, User user, String artistName) throws QueryException {
        ArrayList<Document> artists = new ArrayList<Document>();
        Document artist = new Document("name",artistName).append("addBy",user.getName());
        artists.add(artist);


        MongoCollection artistCollection = database.getCollection("Artist");
        artistCollection.insertOne(artist);

        MongoCollection SongCollection=database.getCollection("Song");

        Document document = new Document("title",title).append("genre",genre);
        document.append("artist",artists);
        document.append("addBy",new Document("name",user.getName()));

        SongCollection.insertOne(document);
    }

    @Override
    public void addArtistToSong(Artist artist, Song song, User user) throws QueryException {
        MongoCollection collection = database.getCollection("Song");





    }

    @Override
    public int addMedia(String title, String genre, User user) throws QueryException {


        return 0;
    }

    @Override
    public void addUser(String userName) throws QueryException {
        MongoCollection collection=database.getCollection("User");
        Document document=new Document("userName",userName);
        collection.insertOne(document);

    }

    @Override
    public void addReview(String reviewText, int rating, User user, int mediaId) throws QueryException {

    }

    @Override
    public Movie getMovieById(int id) throws QueryException {
        return null;
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
        MongoCollection collection= database.getCollection("Director");
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()){
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

        return null;
    }

    @Override
    public ArrayList<Movie> getMovies() throws QueryException {
        ArrayList<Movie> movies= new ArrayList<>();

        MongoCollection collection = database.getCollection("Movie");

        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()){
            Document obj = cursor.next();

            Document subObj= (Document) obj.get("director");
            Director director = new Director(subObj.getString("name"));

            subObj = (Document) obj.get("user");
             User user = new User(-1,subObj.getString("userName"));

            Movie movie = new Movie(obj.getString("title"),obj.getString("genre"),director,user);
            movies.add(movie);
        }
        cursor.close();
        return movies;
    }

    @Override
    public ArrayList<Album> getAlbums() throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Movie> getMoviesByTitle(String title) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Song> getSongsByTitle(String title) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Album> getAlbumsByTitle(String title) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Movie> getMoviesByGenre(String genre) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Song> getSongsByGenre(String genre) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Album> getAlbumsByGenre(String genre) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Movie> getMoviesByDirector(String directorName) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Song> getSongsByArtist(String artistName) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Album> getAlbumsByArtist(String artistName) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Song> getSongsByRating(int rating) throws QueryException {
        return null;
    }

    @Override
    public ArrayList<Movie> getMoviesByRating(int rating) throws QueryException {
        return null;
    }

    @Override
    public User getUserByName(String userName) throws QueryException {
        return null;
    }
}
