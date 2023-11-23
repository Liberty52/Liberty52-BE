package com.liberty52.main.service.repository;

//import com.liberty52.main.global.data.DBInitConfig;
//import com.liberty52.main.global.data.DBInitConfig.DBInitService;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ReviewQueryDslRepositoryImplTest {
    /*TODO: -- 테스트 수정 필요*/
//
//    @Autowired
//    EntityManager em;
//
//    @Autowired
//    DBInitConfig config;
//
//    JPAQueryFactory qf;
//    Orders order;
//    Product product;
//
//    @Autowired
//    ReviewQueryDslRepository reviewQueryDslRepository;
//
//    @BeforeEach
//    void beforeEach() {
//        qf = new JPAQueryFactory(em);
//        order = DBInitService.getOrder();
//        product = DBInitService.getProduct();
//    }
//
//    @AfterEach
//    void afterEach() {
//        em.clear();
//    }
//
//    @Test
//    void photoFilterTest_Filtering() throws Exception {
//        //given
//        ReviewRetrieveResponse response = reviewQueryDslRepository.retrieveReview(
//                product.getId(), order.getAuthId(), PageRequest.of(0, 3), true);
//        //when
//        assertThat(response.getContents().size()).isSameAs(1);
//        assertThat(response.getTotalLastPage()).isSameAs(1L);
//    }
//
//    @Test
//    void photoFilterTest_NotFiltering() throws Exception {
//        //given
//        PageRequest request = PageRequest.of(0, 10);
//        ReviewRetrieveResponse response = reviewQueryDslRepository.retrieveReview(
//                product.getId(), order.getAuthId(), request, false);
//
//        assertThat(response.getContents().size()).isSameAs(10);
//    }

}