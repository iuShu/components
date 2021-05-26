-- seckilling.consistency

create table consistency (
  `id` bigint NOT NULL AUTO_INCREMENT primary key,
  `quantity` int unsigned default 0
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci