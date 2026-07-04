package com.todaysscene.backend.config;

import com.todaysscene.backend.domain.Mood;
import com.todaysscene.backend.domain.MoodReason;
import com.todaysscene.backend.domain.Movie;
import com.todaysscene.backend.repository.MoodRepository;
import com.todaysscene.backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

        private final MoodRepository moodRepository;
        private final MovieRepository movieRepository;

        @Override
        @Transactional
        public void run(String... args) throws Exception {
                if (moodRepository.count() > 0) {
                        return;
                }

                initMood("melancholy",
                                Arrays.asList(152601, 153, 38, 334541, 376867, 84892, 62215, 142, 428449, 2841, 489,
                                                1124, 666277, 976893, 906126, 508883, 965150, 466272),
                                Arrays.asList("혼자 있는 밤, 이 영화가 조용히 곁에 있어줄 거예요", "잔잔하지만 오래 마음에 남는 여운이 있는 영화예요",
                                                "슬프지만 동시에 아름다운, 묘한 감동의 영화예요", "비 오는 밤 따뜻한 음료 한 잔이랑 보면 완벽해요",
                                                "엔딩 크레딧 올라가도 한참 자리에서 못 일어날 거예요", "혼자 있는 시간을 더 깊게 만들어주는 영화예요",
                                                "먹먹한 감정이 오래 가슴에 남는 영화예요", "지치고 쓸쓸한 밤, 조용히 위로해주는 영화예요"));

                initMood("excited",
                                Arrays.asList(27205, 155, 76341, 299536, 245891, 603, 280, 1571, 575264, 1726, 118340,
                                                533535, 569094, 746036, 718821, 911430, 603692, 786892, 653346,
                                                1242898),
                                Arrays.asList("무조건 몰입 보장. 중간에 자리를 떠날 수 없어요", "사운드 좋은 환경에서 보면 완전히 다른 경험이에요",
                                                "짜릿함을 넘어 충격까지 — 강렬한 밤 보장", "아드레날린이 폭발하는 액션 시퀀스가 압권이에요",
                                                "큰 화면에서 봐야 진짜 맛이 나는 영화예요", "심장이 뛰는 걸 느끼고 싶은 밤에 딱이에요",
                                                "시작부터 끝까지 한순간도 지루할 틈이 없어요", "에너지가 필요한 날 밤, 보고 나면 기분 전환 완료"));

                initMood("romantic",
                                Arrays.asList(122906, 11036, 313369, 597, 76, 508, 19913, 4348, 50646, 13, 937287,
                                                1072790, 976573, 1000075, 666277, 950396, 1218925),
                                Arrays.asList("설레는 감정 그 자체를 2시간 동안 느낄 수 있어요", "연애 경험이 있는 사람이라면 누구나 공감해요",
                                                "보고 나서 한동안 OST가 머릿속을 맴돌 거예요", "두근거리는 마음이 그리워질 때 딱이에요",
                                                "첫사랑이 떠오르는, 감성 충만한 영화예요", "소중한 사람과 함께 봐도 좋은 따뜻한 영화예요",
                                                "마음이 몽글몽글해지는 영화예요", "사랑이라는 감정의 모든 색깔을 보여주는 영화예요"));

                initMood("dark",
                                Arrays.asList(807, 274, 210577, 419430, 496243, 11324, 146233, 1593, 242582, 694, 539,
                                                1226578, 945961, 929590, 1242898, 1038392, 762441, 1111873, 1064028,
                                                1173559),
                                Arrays.asList("긴장감이 극에 달하는 밤, 이 영화라면 충분해요", "보고 나서 오래 생각하게 되는, 여운이 짙은 영화예요",
                                                "몰입도 극강의 심리 스릴러, 집중하고 보세요", "소름이 돋는 분위기를 즐기고 싶은 밤에요",
                                                "어두운 인간 본성을 들여다보는 영화예요", "결말까지 숨이 차오르는 긴장감의 연속이에요",
                                                "한 번 보면 잊히지 않는 강렬한 영화예요", "예상치 못한 반전이 기다리고 있어요"));

                initMood("cozy",
                                Arrays.asList(129, 8392, 257211, 773, 324852, 194, 24803, 16859, 2062, 13, 120467,
                                                1022789, 840430, 1022796, 1084242, 1327819, 976893, 1002266, 1226863,
                                                939243),
                                Arrays.asList("담요 덮고 따뜻한 음료 한 잔과 함께 보면 완벽해요", "보고 나면 지금 이 순간이 소중하게 느껴져요",
                                                "지치고 힘든 날, 꼭 안아주는 것 같은 영화예요", "마음이 따뜻해지는 잔잔한 위로가 되는 영화예요",
                                                "온 가족이 같이 봐도 좋은 따뜻한 영화예요", "하루의 피로가 풀리는, 마음을 어루만져주는 영화예요",
                                                "슬며시 미소짓게 만드는, 그런 영화예요", "추운 계절에 이불 속에서 보면 완벽해요"));

                initMood("wonder",
                                Arrays.asList(157336, 19995, 671, 120, 49047, 329865, 62, 286217, 24428, 13475, 119450,
                                                693134, 872585, 1045631, 83533, 687163, 653346, 539972, 786892, 823464),
                                Arrays.asList("사운드 좋은 환경에서 보면 경험이 완전히 달라져요", "끝나고 한참 동안 아무 말도 하고 싶지 않아질 영화예요",
                                                "영화라기보다 하나의 우주적 경험에 가까워요", "큰 화면, 좋은 사운드 시스템에서 봐야 제맛이에요",
                                                "인간이 얼마나 작은 존재인지 느끼게 되는 영화예요", "세상의 광활함을 몸으로 느끼는 영화예요",
                                                "경이로움이라는 단어의 정확한 의미를 알게 돼요", "영화관에서 봤어야 했다는 후회가 남을 작품이에요"));

                initMood("funny",
                                Arrays.asList(18785, 8363, 293660, 64688, 12133, 14160, 9806, 105, 9536, 585, 2062,
                                                346698, 1022787, 836018, 533535, 1084242, 746036, 1198994, 1327819,
                                                1266127),
                                Arrays.asList("웃고 싶은데 내용도 있었으면 하는 밤에 딱이에요", "울다가 웃다가, 기분이 정화되는 느낌이에요",
                                                "엔딩에서 같이 노래 부르고 싶어질 거예요", "실컷 웃고 나면 스트레스가 싹 풀려요",
                                                "가볍게 즐기고 싶은 날 밤에 완벽해요", "웃음 코드가 통통 튀는, 부담 없는 영화예요", "친구랑 같이 보면 더 재밌어요",
                                                "복잡한 생각 다 내려놓고 그냥 즐기세요"));

                initMood("thoughtful",
                                Arrays.asList(496243, 329865, 37165, 603, 550, 37799, 152601, 13, 11324, 77, 398818,
                                                1422, 872585, 792307, 467244, 929590, 936075, 906126, 666277, 687163,
                                                1330021),
                                Arrays.asList("보고 나서 혼자서 한참 생각하게 되는 영화예요", "보고 나면 SNS 알림을 잠깐 꺼두고 싶어져요",
                                                "보고 나면 인간 관계에 대해 한참 생각하게 돼요", "엔딩 크레딧 올라갈 때 멍하니 앉아있게 돼요",
                                                "한 번 더 봐야 진짜 의미가 보이는 영화예요", "인생에 대해 다시 생각하게 만드는 영화예요",
                                                "보고 나면 누군가와 이야기 나누고 싶어져요", "오래 기억에 남는, 묵직한 영화예요"));

                System.out.println("✅ 프론트엔드 데이터(MOOD_DATA)가 백엔드 DB로 모두 이관되었습니다!");
        }

        private void initMood(String moodId, List<Integer> movieIds, List<String> reasons) {
                Mood mood = new Mood();
                mood.setMoodId(moodId);

                for (Integer movieId : movieIds) {
                        Movie movie = movieRepository.findById(movieId).orElse(null);
                        if (movie == null) {
                                movie = new Movie();
                                movie.setMovieId(movieId);
                                movieRepository.save(movie);
                        }
                        mood.getMovies().add(movie);
                }

                for (String reasonText : reasons) {
                        MoodReason reason = new MoodReason();
                        reason.setMood(mood);
                        reason.setReasonText(reasonText);
                        mood.getReasons().add(reason);
                }

                moodRepository.save(mood);
        }
}
