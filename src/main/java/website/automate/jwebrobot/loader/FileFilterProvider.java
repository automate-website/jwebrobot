package website.automate.jwebrobot.loader;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;

@Service
public class FileFilterProvider {

    private static final String IGNORE_FILE_NAME = ".wamlignore";

    private static final List<String> IGNORE_WILDCARDS = asList(
        "report",
        "docker-compose.yml",
        ".travis-ci",
        ".travis-ci.yml",
        ".gitlab-ci",
        ".gitlab-ci.yml"
    );

    private static final IOFileFilter IGNORE_FILE_FILTER = new WildcardFileFilter(IGNORE_WILDCARDS);

    private static final IOFileFilter YAML_FILE_FILTER = new WildcardFileFilter(new String[] {"**.yaml", "**.yml"});

    public IOFileFilter getFileFilter(File base){
        IOFileFilter ignoreWildcardFileFilter = getWildcardFileFilter(base);
        if(ignoreWildcardFileFilter != null){
            return new AndFileFilter(YAML_FILE_FILTER, new NotFileFilter(ignoreWildcardFileFilter));
        }
        return YAML_FILE_FILTER;
    }

    private IOFileFilter getWildcardFileFilter(File file){
        if(!file.isDirectory()){
            return null;
        }
        File ignoreWildcardFile = new File(file, IGNORE_FILE_NAME);
        if(!ignoreWildcardFile.exists()
            || !ignoreWildcardFile.isFile()){
            return IGNORE_FILE_FILTER;
        }

        return asFilter(ignoreWildcardFile);
    }

    private WildcardFileFilter asFilter(File ignoreWildcardFile){
        try {
            List<String> wildcards = Files .readAllLines(ignoreWildcardFile.toPath());
            wildcards.addAll(IGNORE_WILDCARDS);
            return new WildcardFileFilter(wildcards, IOCase.INSENSITIVE);
        } catch (Exception e) {
            throw new RuntimeException(format(
                "Can not read from file {0}.",
                ignoreWildcardFile.getAbsolutePath()), e);
        }
    }
}
