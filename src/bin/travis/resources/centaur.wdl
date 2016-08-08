#        ./test_cromwell.sh -j${cromwell_jar} -c/cromwell_root/${conf} -r/cromwell_root -t ${secret}
task centaur {
    String cromwell_branch
    File conf
    File pem
    File cromwell_jar
    File token
    String secret = read_string(token)

    command<<<
        mkdir -p /cromwell_root/tmp/ivy2
        export SBT_OPTS=-Dsbt.ivy.home=/cromwell_root/tmp/.ivy2
        git clone https://github.com/broadinstitute/centaur.git
        cd centaur
        git checkout develop
        sbt "test-only * -- -n forkjoin"
        sleep 20
        ^C
        curl -X GET --header "Accept: application/json" "http://localhost:8000/api/workflows/v1/query?status=Running&name=forkjoin"
        grep '[a-z][0-9]\{3\}[a-z]\{2\}[0-9][a-z]-[0-9][a-z]\{3\}'
    >>>

    output {
       File cromwell_log = "/cromwell_root/logs/cromwell.log"
       File centaur_log_file = "/cromwell_root/logs/centaur.log"
    }

    runtime {
        docker: "geoffjentry/centaur-cromwell:latest" 
        cpu: "32"
        zones: "us-central1-b"
        failOnStderr: false
    }
}
workflow centaur {
    call centaur
}
