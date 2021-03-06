package asteroid.utils

class CollectLabelsSpecExample {

    @CollectLabels
    def execute() {
        first: 'john'
        println '=====>1'

        second: 'peter'
        println '=====>2'

        third: 'paul'
        println '=====>3'

        fourth: [
            name: 'paul',
            age: 22
        ]
        println "=====>4"
    }
}
