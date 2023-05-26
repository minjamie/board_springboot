package com.example.board.repository;

// org.springframework.data.jpa - 스프링 data jpa
import com.example.board.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

// Spring Data JPA Repository 완성 - DAO 만든 것과 동일
// 스프링 data jpa UserRepository 구현하는 Bean 자동 생성 - Proxy 객체 활용
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name); // query method - Spring Data JPA

    // where name = ? and email = ?

    Optional<User> findByNameAndEmail(String name, String email);

    // where name = ? or email = ?
    List<User> findByNameOrEmail(String name, String email);

    // where user_id between ? and ?
    List<User> findByUserIdBetween(int startUserId, int endUserId);

    // where user_id < ?

    List<User> findByUserIdLessThan(int userId);

    // where user_id <= ?
    List<User> findByUserIdLessThanEqual(int userId);

    // where user_id > ?
    List<User> findByUserIdGreaterThan(int userId);

    // where user_id >= ?
    List<User> findByUserIdGreaterThanEqual(int userId);

    // where regdate > ?
    List<User> findByRegdateAfter(LocalDateTime day);

    // where regdate < ?
    List<User> findByRegdateBefore(LocalDateTime day);

    // where name is null
    List<User> findByNameIsNull();

    // where name is not null
    List<User> findByNameIsNotNull();

    // where name like ?
    List<User> findByNameLike(String name);


    // where name like '입력한 값%'
    List<User> findByNameStartingWith(String name);


    // where name like '%입력한 값'
    List<User> findByNameEndingWith(String name);


    // where name like '%입력한 값%'
    List<User> findByNameContaining(String name);

    // order by name asc
    List<User> findByOrderByNameAsc();

    // order by name desc
    List<User> findByOrderByNameDesc();

    // where regdate > ? order by name desc
    List<User> findByRegdateAfterOrderByNameDesc(LocalDateTime day);


    // where <> name
    List<User> findByNameNot(String name);

    // where user_id in (....)
    List<User> findByUserIdIn(Collection<Integer> userIds);


    // where user_id not in (....)
    List<User> findByUserIdNotIn(Collection<Integer> userIds);

    // where flag = true flag는 boolean 형 타입 컬럼
//    List<User> findByFlagTrue();

    // where flag = false flag는 boolean 형 타입 컬럼
//    List<User> findByFlagFalse();

    // select count(*) from user3
    Long countBy();

    // select count(*) where name like?
    Long countByNameLike(String name);

    // 결과가 있는 지 확인
    boolean existsByNameLike(String name);


    boolean existsByEmail(String email);

    // select * from user3 where name = ?
    // select 한 건수만큼 delete from user3 where user_id = ?
    int deleteByNameLike(String name);

    // select distinct ...where name like ? - 중복제거
    List<User> findDistinctByNameLike(String name);


    //결과 중 2건만
    List<User> findFirst2By();
    List<User> findTop2By();

    // 페이징 처리
    Page<User> findBy(Pageable pageable);
    Page<User> findNameBy (String name, Pageable pageable);


    Optional<com.example.board.domain.User> findByEmail(String email);
}
