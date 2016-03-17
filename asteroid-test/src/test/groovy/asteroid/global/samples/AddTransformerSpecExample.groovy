package asteroid.global.samples

class AddTransformerSpecExample {

    static class Input {
        Long materialId
    }

    Input execute() {
        return new Input(materialId: 1L)
    }
}
