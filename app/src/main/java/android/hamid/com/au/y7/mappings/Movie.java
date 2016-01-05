package android.hamid.com.au.y7.mappings;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamid on 13/11/2015.
 */
public class Movie {
	@SerializedName("name")
	private String myName;
	@SerializedName("start_time")
	private String myStartTime;
	@SerializedName("end_time")
	private String myEndTime;
	@SerializedName("channel")
	private String myChannel;
	@SerializedName("rating")
	private String myRating;
// We dont need setters because gson does everythin by itself.
	public String getMyName() {
		return myName;
	}

	public String getMyStartTime() {
		return myStartTime;
	}

	public String getMyEndTime() {
		return myEndTime;
	}

	public String getMyChannel() {
		return myChannel;
	}

	public String getMyRating() {
		return myRating;
	}
}
