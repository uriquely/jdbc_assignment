--======================================================
-- 관리자(system)계정으로 실행 영역
--======================================================
--student 계정 생성 및 권한 부여
create user student
identified by student
default tablespace users;

--권한 부여
grant connect, resource to student;

--======================================================
-- student 계정으로 실행 영역
--======================================================
--member 테이블 생성(회원관리)

create table member (
    member_id varchar2(15), --아이디
    password varchar2(15) not null, --비밀번호
    member_name varchar2(50) not null, --이름
    gender char(1), --성별
    age number, --나이
    email varchar2(300), --이메일
    phone char(11) not null, --전화번호
    address varchar2(300), --주소
    hobby varchar2(100), --취미
    enroll_date date default sysdate, --등록일
    constraint pk_member_id primary key(member_id),
    constraint ck_gender check(gender in('M', 'F'))
);

--sample data 추가
insert into member
values(
    'honggd', '1234', '홍길동', 'M', 30, 'honggd@naver.com', '01012341234',
    '서울 강남구 테헤란로', '등산, 그림, 요리', default
);

insert into member
values(
    'gogd', '1234', '고길동', 'M', 35, 'gogd@naver.com', '01012345678',
    '서울 서초구', '산책, 영화', default
);

insert into member
values(
    'leegd', '1234', '이길동', 'F', 28, 'leegd@naver.com', '01033334444',
    '서울 영등포구', '음악', default
);

insert into member
values(
    'sinsa', '1234', '신사임당', 'F', 48, 'sinsa@naver.com', '01099998888',
    '경기도 광주', '요리, 검술', default
);

insert into member
values(
    'sejong', '1234', '세종대왕', 'M', 76, 'sejong@naver.com', '01033332345',
    '서울시 중구', '독서, 육식', default
);

rollback;

select * from member;

commit;

select *
from member
where member_id = 'potato';