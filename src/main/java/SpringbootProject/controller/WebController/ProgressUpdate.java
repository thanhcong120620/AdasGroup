package SpringbootProject.controller.WebController;

public class ProgressUpdate {

	 private int updateNumber;
	    private int progressPercent;
	    private long timeRemaining;

	    public ProgressUpdate(int updateNumber, int progressPercent, long timeRemaining) {
	        this.updateNumber = updateNumber;
	        this.progressPercent = progressPercent;
	        this.timeRemaining = timeRemaining;
	    }

    // Getters and Setters
    public int getUpdateNumber() {
        return updateNumber;
    }

    public void setUpdateNumber(int updateNumber) {
        this.updateNumber = updateNumber;
    }

    public int getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}

