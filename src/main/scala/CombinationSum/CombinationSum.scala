package CombinationSum

import scala.collection.mutable.ArrayBuffer

/**
  * Created by iodone on {19-9-11}.
  */

/**
  * 解题报告
  * 
  * 说明：采用深度搜索和递归的方法
  * 
  * 思路一：深度搜索遍历候选数组每个成员，都可以作为一个搜索入口。为避免重复，剪枝策略：每个分支仅
  * 搜索包含自身的后续成员。如果最后剩下的target < 0 表明该分支不符合。
  * Comb(S, target) = [ S(i) U Comb(S[i:], target - S(i)) | i < S.length]
  * 实现方式可以采用：完全递归和dfs的遍历两种
  * 
  * 思路二：递归方法。该问题可以拆解成两部分：包含第一个成员和不包含第一个成员的组合数。
  * Comb(S,target) = [ {S(i) U Comb(S, target-S(i))} U Comb(S.remove(S(i)), target) | i < S.length]
  * 这个方式实现更适合计算组合数。
  * 
  */
// 回溯
object Solution {

  def combinationSum(candidates: Array[Int], target: Int): List[List[Int]] = {
    comb(candidates, 0, target)
    // genComb(candidates, target)
  }

  // prune
  /*
     candidates = C,
     comb(s) = { {Ci} U comb(s-Ci)| i < C.length}
   */
  def comb(xs: Array[Int], start: Int, left: Int): List[List[Int]] = {
    if (left == 0) List(List())
    else if (left < 0) List(List(-1))
    else {
      for {
        i <- (start until xs.length).toList
        cs <- comb(xs, i, left - xs(i))
        if cs != List(-1)
      } yield xs(i) :: cs
    }
  }

  // dfs + 回溯
  def genComb(candidates: Array[Int], target: Int): List[List[Int]] = {
    val buf = ArrayBuffer[List[Int]]()
    def dfs(start: Int, left: Int, tmp: List[Int]): Unit = {
      if (left == 0) buf += tmp
      else {
        for (i <- start until candidates.length) {
          if (left - candidates(i) >= 0)
            dfs(i, left - candidates(i), tmp ::: candidates(i) :: Nil)
          tmp.dropRight(1)
        }
      }
    }
    buf.toList
  }

  def main(args: Array[String]): Unit = {
    val x = combinationSum(Array(2,3,6,7), 7)
    println(x)
  }

}
