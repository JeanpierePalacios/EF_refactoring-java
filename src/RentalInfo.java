import java.util.HashMap;
import java.util.Map;

public class RentalInfo {
  
  private Map<String, Movie> movies = new HashMap<>();

  public RentalInfo() {
    initializeMovies();
  }

  public String statement(Customer customer) {
    double totalAmount = 0;
    int frequentEnterPoints = 0;
    StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");

    for (MovieRental rental : customer.getRentals()) {
      double thisAmount = calculateAmount(rental);
      frequentEnterPoints += calculateFrequentEnterPoints(rental);

      result.append("\t").append(getMovieTitle(rental.getMovieId())).append("\t").append(thisAmount).append("\n");
      totalAmount += thisAmount;
    }

    result.append("Amount owed is ").append(totalAmount).append("\n");
    result.append("You earned ").append(frequentEnterPoints).append(" frequent points\n");

    return result.toString();
  }

  private void initializeMovies() {
    movies.put("F001", new Movie("You've Got Mail", "regular"));
    movies.put("F002", new Movie("Matrix", "regular"));
    movies.put("F003", new Movie("Cars", "childrens"));
    movies.put("F004", new Movie("Fast & Furious X", "new"));
  }

  private double calculateAmount(MovieRental rental) {
    double amount = 0;
    Movie movie = movies.get(rental.getMovieId());
    String code = movie.getCode();

    switch (code) {
      case "regular":
        amount = 2;
        if (rental.getDays() > 2) {
          amount += (rental.getDays() - 2) * 1.5;
        }
        break;
      case "new":
        amount = rental.getDays() * 3;
        break;
      case "childrens":
        amount = 1.5;
        if (rental.getDays() > 3) {
          amount += (rental.getDays() - 3) * 1.5;
        }
        break;
    }

    return amount;
  }

  private int calculateFrequentEnterPoints(MovieRental rental) {
    int points = 1;
    Movie movie = movies.get(rental.getMovieId());
    String code = movie.getCode();

    if (code.equals("new") && rental.getDays() > 2) {
      points++;
    }

    return points;
  }

  private String getMovieTitle(String movieId) {
    Movie movie = movies.get(movieId);
    return movie != null ? movie.getTitle() : "";
  }
}
