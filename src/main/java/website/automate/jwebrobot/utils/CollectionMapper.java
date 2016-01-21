package website.automate.jwebrobot.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CollectionMapper<S, T> implements Mapper<S, T>{

	public List<T> map(Iterable<S> sources){
	    if (sources == null) {
	        return Collections.emptyList();
	    }

	    List<T> targets = new ArrayList<T>();
		for (S source : sources) {
			targets.add(map(source));
		}

		return targets;
	}
}
