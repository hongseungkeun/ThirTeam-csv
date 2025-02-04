package com.sparta.csv.global.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UriUtil {

    public static URI create(String path, Long id) {
        return UriComponentsBuilder.fromPath(path)
                .buildAndExpand(id)
                .toUri();
    }

    public static URI createUri(String path) {
        return UriComponentsBuilder.fromPath(path)
            .build(true)
            .toUri();
    }
}
