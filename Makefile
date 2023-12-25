GRADLE = ./gradlew

.PHONY: all
all: run

.PHONY: lint
lint: spotless prettier yamllint

.PHONY: build
build:
	$(GRADLE) clean assemble

.PHONY: test
test:
	$(GRADLE) clean test

.PHONY: run
run:
	$(GRADLE) clean run

.PHONY: clean
clean:
	$(GRADLE) clean

.PHONY: spotless
spotless:
	$(GRADLE) spotlessApply

.PHONY: prettier
prettier:
	prettier --write '**/*.{md,yml,yaml}'

.PHONY: yamllint
yamllint:
	yamllint --strict .
