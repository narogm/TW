var waterfall = require('async-waterfall')

var Fork = function() {
    this.state = 0;
    return this;
}

Fork.prototype.acquire = function(cb) { 
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.

    var time = 1;
    var getFork = function (timeToWait, fork){
        setTimeout(function(){
            if(fork.state == 0){
                fork.state = 1;
                if (cb) cb();
            }
            else{
                // console.log('waiting for fork ' + timeToWait);
                time = time * 2;
                getFork(time, fork);
            }
        }, timeToWait);
    }

    getFork(1, this);
}

Fork.prototype.release = function(cb) { 
    this.state = 0;
    if (cb) cb(); 
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

    if(count != 0){
        forks[f1].acquire(function(){
            forks[f2].acquire(function(){
                setTimeout(function(){
                    waterfall([
                        function(cb){
                            console.log('Philosopher ' + id + 'is eating');
                            cb();
                        },
                        function(cb){
                            forks[f1].release(cb);
                        },
                        function(cb){
                            forks[f2].release(cb);
                        },
                        function(cb){
                            philosophers[id].startNaive(count - 1);
                        }
                    ])
                },32);
            });
        });
    } 
}

Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        f1 = (this.id % 2 == 1 ? this.f1 : this.f2),
        f2 = (this.id % 2 == 1 ? this.f2 : this.f1),
        id = this.id;
    
    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    if(startTimes[id] == undefined) startTimes[id] = new Date().getTime();

    if(count != 0){
        forks[f1].acquire(function(){
            forks[f2].acquire(function(){
                setTimeout(function(){
                    waterfall([
                        // function(cb){
                        //     console.log('Philosopher ' + id + 'is eating');
                        //     cb();
                        // },
                        function(cb){
                            forks[f1].release(cb);
                        },
                        function(cb){
                            forks[f2].release(cb);
                        },
                        function(cb){
                            philosophers[id].startAsym(count - 1);
                        }
                    ])
                },32);
            });
        });
    }
    else console.log(id, ',', new Date().getTime()-startTimes[id]);    
}

Philosopher.prototype.startConductor = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
    if(startTimes[id] == undefined) startTimes[id] = new Date().getTime();

    if(count != 0){
        conductor.acquire(
        function(){
            forks[f1].acquire(function(){
                forks[f2].acquire(function(){
                    setTimeout(function(){
                        waterfall([
                            // function(cb){
                            //     console.log('Philosopher ' + id + 'is eating');
                            //     cb();
                            // },
                            function(cb){
                                forks[f1].release(cb);
                            },
                            function(cb){
                                forks[f2].release(cb);
                            },
                            function(cb){
                                conductor.release(cb);
                            },
                            function(){
                                philosophers[id].startConductor(count - 1);
                            }
                        ])
                    },32);
                });
            });
        });
    }
    else console.log(id, ',', new Date().getTime()-startTimes[id]);
}

var Conductor = function(){
    this.freeSeats = 4;
    return this;
}

Conductor.prototype.acquire = function(cb){
    var time = 1;
    var getSeat = function (timeToWait, conductor){
        setTimeout(function(){
            if(conductor.freeSeats > 0){
                conductor.freeSeats--;
                if (cb) cb();
            }
            else{
                // console.log('waiting for seat ' + timeToWait);
                time = time * 2;
                getSeat(time, conductor);
            }
        }, timeToWait);
    }

    getSeat(1, conductor);
}

Conductor.prototype.release = function(cb){
    conductor.freeSeats++;
    if (cb) cb();
}

var N = 5;
var forks = [];
var philosophers = [];
var startTimes = [];
var conductor = new Conductor();
for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < N; i++) {
    // philosophers[i].startNaive(10);
    // philosophers[i].startAsym(10);
    philosophers[i].startConductor(10);
}