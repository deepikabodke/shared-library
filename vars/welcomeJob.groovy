import hudson.tasks.test.AbstractTestResultAction

def call(String name = 'User') {
    echo "Welcome, ${name}."

    AbstractTestResultAction testResultAction =  currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
    if (testResultAction != null) {
        echo "Tests: ${testResultAction.failCount} / ${testResultAction.failureDiffString} failures of ${testResultAction.totalCount}.\n\n"
    }
     /*def testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
     def summary = ""

     if (testResultAction != null) {
         def total = testResultAction.getTotalCount()
         def failed = testResultAction.getFailCount()
         def skipped = testResultAction.getSkipCount()

         summary = "Test results:\n\t"
         summary = summary + ("Passed: " + (total - failed - skipped))
         summary = summary + (", Failed: " + failed)
         summary = summary + (", Skipped: " + skipped)
     } else {
         summary = "No tests found"
     }
     return summary*/

    /*@NonCPS
    def testStatuses() {
        def testStatus = ""
        AbstractTestResultAction testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
        if (testResultAction != null) {
            def total = testResultAction.totalCount
            def failed = testResultAction.failCount
            def skipped = testResultAction.skipCount
            def passed = total - failed - skipped
            testStatus = "Test Status:\n  Passed: ${passed}, Failed: ${failed} ${testResultAction.failureDiffString}, Skipped: ${skipped}"

            if (failed == 0) {
                currentBuild.result = 'SUCCESS'
            }
        }
        return testStatus
    }*/
}