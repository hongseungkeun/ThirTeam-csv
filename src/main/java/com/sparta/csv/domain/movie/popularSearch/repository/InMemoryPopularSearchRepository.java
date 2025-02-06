//package com.sparta.csv.domain.movie.popularSearch.repository;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.Comparator;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//
//@Repository
//@RequiredArgsConstructor
//public class InMemoryPopularSearchRepository implements PopularSearchRepository {
//
//    private final Map<String, Long> searchCountMap = new ConcurrentHashMap<>();
//
//    @Override
//    public void incrementCount(String search) {
//        searchCountMap.merge(search, 1L, Long::sum);
//    }
//
//    @Override
//    public Map<String, Long> findAll() {
////        searchCountMap.put("범죄도시",24L);
////        searchCountMap.put("제목1",223L);
////        searchCountMap.put("제목2",256L);
////        searchCountMap.put("제목3",251L);
////        searchCountMap.put("제목4",251L);
////        searchCountMap.put("제목5",256L);
////        searchCountMap.put("제목6",212L);
////        searchCountMap.put("제목7",14L);
////        searchCountMap.put("제목8",124L);
////        searchCountMap.put("제목9",324L);
//
//        return searchCountMap.entrySet().stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (oldValue, newValue) -> oldValue,
//                        LinkedHashMap::new
//                ));
//    }
//
//}
