def call(String name = 'User') {
    echo "Welcome, ${name}."

    def getTestSummary = { ->
     def testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
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
     return summary
 }
}