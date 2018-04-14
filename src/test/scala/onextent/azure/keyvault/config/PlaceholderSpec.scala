package onextent.azure.keyvault.config

import org.scalatest._

import scala.collection.mutable

class PlaceholderSpec extends FlatSpec {

  "A Stack" should "pop values in last-in-first-out order" in {
    //noinspection ScalaDeprecation
    val stack = new mutable.Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    //noinspection ScalaDeprecation
    val emptyStack = new mutable.Stack[String]
    assertThrows[NoSuchElementException] {
      emptyStack.pop()
    }
  }
}
