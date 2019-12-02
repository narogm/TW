var Fork = function() {
    this.state = 0;
    return this;
}

Fork.prototype.acquire = function(timeToWait, cb) { 
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
    if(this.state == 0){
        this.state = 1;
        // setTimeout(function(){
            if (cb) cb();
        // }, 0);
        // return this;
    }
    else{
        console.log('waiting for fork ' + timeToWait);
        setTimeout(this.acquire(timeToWait * 2, cb), timeToWait);
    }
}

Fork.prototype.release = function() { 
    this.state = 0; 
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
}

//oddane
Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    for(var i = 0; i<count; i++){
        forks[f1].acquire(1, function(){
            forks[f2].acquire(1, function(){
                setTimeout(function(){
                    console.log('Philosopher' + id + ' is eating');
                },0);
                forks[f1].release();
                forks[f2].release();
            });
        });
    }
}

Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        f1 = (this.id % 2 == 1 ? this.f1 : this.f2),//this.f1,   //left
        f2 = (this.id % 2 == 1 ? this.f2 : this.f1), //this.f2,   //right
        id = this.id;
    
    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
    for(var i = 0; i<count; i++){
        setTimeout(function(){
            // if(id % 2 == 1){
                forks[f1].acquire(1, function(){
                    console.log(forks[f1].state);
                    forks[f2].acquire(1, function(){
                        setTimeout(function(){
                            console.log('Philosopher ' + id + 'is eating');
                        }, 10);
                        forks[f1].release();
                        forks[f2].release();
                        console.log('Philosopher ' + id + 'finished cycle ' + i);
                    });
                });
            // }
            // else{
            //     forks[f2].acquire(1, function(){
            //         forks[f1].acquire(1);
            //     });
            // }
        },0);
    }
}

Philosopher.prototype.startConductor = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}


var N = 5;
var forks = [];
var philosophers = []
for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < N; i++) {
    // philosophers[i].startNaive(10);
    philosophers[i].startAsym(3);
}