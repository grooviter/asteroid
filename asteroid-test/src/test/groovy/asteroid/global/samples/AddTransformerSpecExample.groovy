package asteroid.global.samples

@TrickOrTrait(Something)
class AddTransformerSpecExample {

    static class Input {
        Long materialId
    }

    Input execute() {
        return new Input(materialId: 1L)
    }
}

@interface TrickOrTrait {
    Class value();
}

trait Something {
    String saySomething() {
        return "something"
    }
}
