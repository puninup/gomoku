package si.gomoku;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Tomasz Urbas
 */
public class CountDownTimer extends Observable {

    private boolean running;

    private long timeInMilliseconds;
    private long setUpTimeInMilliseconds;
    private int delayInMilliseconds = 20;
    private Timer timer;
    private StringProperty stringTime = new SimpleStringProperty("00:00");

    public CountDownTimer() {
        timeInMilliseconds = 0;
        prepareTimerTask();
    }

    private TimerTask prepareTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                if (!running) {
                    this.cancel();
                    return;
                }

                timeInMilliseconds -= delayInMilliseconds;
                updateStringTime();

                if (timeInMilliseconds <= 0) {
                    endOfTime();
                }
            }
        };
    }

    public void start() {
        running = true;
        timer = new Timer();
        updateStringTime();
        timer.scheduleAtFixedRate(prepareTimerTask(), delayInMilliseconds, delayInMilliseconds);
    }

    public void stop() {
        running = false;
        Optional.ofNullable(timer).ifPresent(Timer::cancel);
    }

    private void endOfTime() {
        stop();
        setChanged();
        notifyObservers();
    }

    public void reset() {
        timeInMilliseconds = setUpTimeInMilliseconds;
    }

    public void setUp(int minutes, int seconds) {
        setUpTimeInMilliseconds = toMilliseconds(minutes, seconds);
    }

    private long toMilliseconds(int minutes, int seconds) {
        return minutes * 60_000 + seconds * 1_000;
    }

    private void updateStringTime() {
        long seconds = (timeInMilliseconds + 999) / 1_000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        String time = String.format("%02d:%02d", minutes, seconds);
        Platform.runLater(() -> stringTime.setValue(time));
    }

    public StringProperty getStringTime() {
        return stringTime;
    }
}
