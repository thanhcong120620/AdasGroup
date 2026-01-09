package FileUtil;

import java.util.LinkedHashSet;
import java.util.Set;

public class StringProcess {
	public  String mergeUnique(String c, String input) {
        Set<String> set = new LinkedHashSet<>();

        // dữ liệu gốc
        for (String s : c.split("; ")) {
            set.add(s);
        }

        // dữ liệu mới
        for (String s : input.split("; ")) {
            set.add(s);
        }

        return String.join("; ", set);
    }
}
