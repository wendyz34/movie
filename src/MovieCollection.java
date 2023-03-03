import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    ArrayList<String> fullGenreList = new ArrayList<String>();
    ArrayList<String> fullCastList = new ArrayList<String>();

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);

    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {

       ArrayList<String>castList;

        for (int i = 0; i < movies.size(); i++)
        {
            String movieCast = movies.get(i).getCast();

            castList = new ArrayList<String>(Arrays.asList(movieCast.split("\\|")));

            for (int a = 0; a <= castList.size() - 1; a++) {
                castList.set(a, castList.get(a).toLowerCase());
            }

            for (int j = 0; j <= castList.size() - 1; j++) {
                if (!fullCastList.contains(castList.get(j))) {
                    fullCastList.add(castList.get(j));
                }
            }
        }
        System.out.print("Enter a cast search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();

        ArrayList<String> result = new ArrayList<String>();

       for (int i = 0; i <= fullCastList.size() - 1; i++) {
            if (fullCastList.get(i).indexOf(searchTerm) != -1) {
                result.add(fullCastList.get(i));
            }
        }

        Collections.sort(result);

        for (int i = 0; i < result.size(); i++)
        {
            String actor = result.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + actor);
        }

        System.out.println("Which cast's movies would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedCast = result.get(choice - 1);

        ArrayList<Movie> appearance = new ArrayList<Movie>();
        for (int i = 0; i <= movies.size() - 1; i++) {
            if (movies.get(i).getCast().toLowerCase().indexOf(selectedCast) != -1) {
                appearance.add(movies.get(i));
            }
        }

        for (int i = 0; i < appearance.size(); i++)
        {
            Movie movie = appearance.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + movie);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice2 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = appearance.get(choice2 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++)
        {
            String keyWord = movies.get(i).getKeywords();
            keyWord = keyWord.toLowerCase();

            if (keyWord.indexOf(searchTerm) != -1)
            {
                results.add(movies.get(i));
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres() {
        ArrayList<String> genre;

        for (int i = 0; i < movies.size(); i++)
        {
            String movieGenre = movies.get(i).getGenres();

            genre = new ArrayList<String>(Arrays.asList(movieGenre.split("\\|")));

            for (int a = 0; a <= genre.size() - 1; a++) {
                genre.set(a, genre.get(a).toLowerCase());
            }

            for (int j = 0; j <= genre.size() - 1; j++) {
                if (!fullGenreList.contains(genre.get(j))) {
                    fullGenreList.add(genre.get(j));
                }
            }
        }

        Collections.sort(fullGenreList);

        for (int i = 0; i < fullGenreList.size(); i++)
        {
            String g = fullGenreList.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + g);
        }


        System.out.println("Which genre would you like to see?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = fullGenreList.get(choice - 1);

        ArrayList<Movie> genreMovies = new ArrayList<Movie>();
        for (int i = 0; i <= movies.size() - 1; i++) {
            if (movies.get(i).getGenres().toLowerCase().indexOf(selectedGenre) != -1) {
                genreMovies.add(movies.get(i));
            }
        }

        for (int i = 0; i < genreMovies.size(); i++)
        {
            Movie movie = genreMovies.get(i);
            int c = i + 1;
            System.out.println("" + c + ". " + movie);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int c2 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = genreMovies.get(c2 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated() {
        ArrayList<Movie>ratings = new ArrayList<Movie>();
        for (int i = 0; i <= movies.size() - 1; i++) {
            ratings.add(movies.get(i));
        }

        for (int i = 1; i <= ratings.size() - 1; i++) {
            int j = i;
            while (j > 0 && ratings.get(j - 1).getUserRating() > ratings.get(j).getUserRating()) {
                Movie temp = ratings.get(j);
                ratings.set(j, ratings.get(j - 1));
                ratings.set(j - 1, temp);
                j--;
            }
        }

        ArrayList<Movie> top = new ArrayList<Movie>();
        for (int i = ratings.size() - 1; i >= ratings.size() - 50; i--)
        {
            top.add(ratings.get(i));
        }
        int num =1;
        for (int i = 0; i <= top.size() - 1; i++)
        {
            Movie movie = top.get(i);

            System.out.println("" + num + ". " + movie + " Rating: " + movie.getUserRating());
            num++;
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = top.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue() {
        ArrayList<Movie> revenue = new ArrayList<Movie>();
        for (int i = 0; i <= movies.size() - 1; i++) {
            revenue.add(movies.get(i));
        }
        for (int i = 1; i <= revenue.size() - 1; i++) {
            int j = i;
            while (j > 0 && revenue.get(j - 1).getRevenue() > revenue.get(j).getRevenue()) {
                Movie temp = revenue.get(j);
                revenue.set(j, revenue.get(j - 1));
                revenue.set(j - 1, temp);
                j--;
            }
        }

        ArrayList<Movie> topRevenue = new ArrayList<Movie>();
        for (int i = revenue.size() - 1; i >= revenue.size() - 50; i--)
        {
            topRevenue.add(revenue.get(i));
        }

        for (int i = 0; i <= topRevenue.size() - 1; i++)
        {
            Movie movie = topRevenue.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + movie + " Revenue: " + movie.getRevenue());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = topRevenue.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}