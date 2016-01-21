package website.automate.jwebrobot.utils;

public interface Mapper<S, T> {

	T map(S source);
	
	void map(S source, T target);
}
