DB_PORT = $$(docker-compose port db 5432 | awk -F':' '{print $$2}' &2> /dev/null || true)

db:
	PGPASSWORD=password psql -U caseworker --port $(DB_PORT) -h 127.0.0.1 caseworker

sass:
	mkdir -p resources/public/css/compiled && \
		sassc \
		--style compressed \
		src/sass/application.sass \
		resources/public/css/compiled/application.css

implode:
	docker-compose stop && docker-compose rm

up:
	docker-compose up -d

.PHONY: test
test:
	lein do test, doo chrome-headless test once
