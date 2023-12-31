GRADLE = ./gradlew
JAVA_ENABLE_ASSERTS = -ea

.PHONY: all
all: run

.PHONY: lint
lint: spotless prettier yamllint

.PHONY: build
build:
	$(GRADLE) clean shadowJar

.PHONY: test
test:
	$(GRADLE) clean test

.PHONY: run
run: build
	java $(JAVA_ENABLE_ASSERTS) -jar ./build/libs/adventofcode*.jar

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
