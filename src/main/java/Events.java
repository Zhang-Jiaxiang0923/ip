import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Events extends Task{
    protected LocalDate start;
    protected LocalDate end;

    public Events(String description, LocalDate start, LocalDate end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString(){
        String startTime = start.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        String endTime = end.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }

    @Override
    public String getSymbol() {
        return "E";
    }
}
