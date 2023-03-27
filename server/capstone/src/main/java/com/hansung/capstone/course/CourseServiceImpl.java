package com.hansung.capstone.course;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;
import com.hansung.capstone.community.PostDTO;
import com.hansung.capstone.community.PostRepository;
import com.hansung.capstone.community.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    private final PostRepository postRepository;

    private final PostService postService;

    @Override
    public PostDTO.PostResponseDTO createCourse(CourseDTO.CreateRequestDTO req, List<MultipartFile> files) throws Exception {
        if (req.getCategory().equals("COURSE")) {
            Course newCourse = Course.builder()
                    .coordinates(req.getCoordinates())
                    .region(CourseRegion.valueOf(req.getRegion().name()))
                    .post(this.postService.createCourseBoardPost(req,files))
                    .build();
            this.courseRepository.save(newCourse);
        }
        return null;
    }

    private String convertCoordinatesToPolyline(List<List<Double>> req){
        StringBuffer sb = new StringBuffer();
        List<LatLng> latLngList = new ArrayList<>();
        for(List<Double> coordinates : req){
            Double lat = coordinates.get(0);
            Double lng = coordinates.get(1);
            LatLng latLng = new LatLng(lat, lng);
            latLngList.add(latLng);
        }
        sb.append(Polyline.encode(latLngList));
        return sb.toString();
    }

    public static class Polyline extends PolylineEncoding {
        public static List<LatLng> decode(final String encodedPath) {

            int len = encodedPath.length();

            final List<LatLng> path = new ArrayList<>(len / 2);
            int index = 0;
            int lat = 0;
            int lng = 0;

            while (index < len) {
                int result = 1;
                int shift = 0;
                int b;
                do {
                    b = encodedPath.charAt(index++) - 63 - 1;
                    result += b << shift;
                    shift += 5;
                } while (b >= 0x1f);
                lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

                result = 1;
                shift = 0;
                do {
                    b = encodedPath.charAt(index++) - 63 - 1;
                    result += b << shift;
                    shift += 5;
                } while (b >= 0x1f);
                lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

                path.add(new LatLng(lat * 1e-6, lng * 1e-6));
            }

            return path;
        }
        public static String encode(final List<LatLng> path) {
            long lastLat = 0;
            long lastLng = 0;

            final StringBuilder result = new StringBuilder();

            for (final LatLng point : path) {
                long lat = Math.round(point.lat * 1e6);
                long lng = Math.round(point.lng * 1e6);

                long dLat = lat - lastLat;
                long dLng = lng - lastLng;

                encode(dLat, result);
                encode(dLng, result);

                lastLat = lat;
                lastLng = lng;
            }
            return result.toString();
        }

        private static void encode(long v, StringBuilder result) {
            v = v < 0 ? ~(v << 1) : v << 1;
            while (v >= 0x20) {
                result.append(Character.toChars((int) ((0x20 | (v & 0x1f)) + 63)));
                v >>= 5;
            }
            result.append(Character.toChars((int) (v + 63)));
        }
    }
}
