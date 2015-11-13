import MySQLdb
import random
from datetime import date

class Pool:
	i = -1

	pool = []
	
	def __init__(self):
		#connect to db and to get a list of people
		
		self.timeStamp = self.getTimeStamp()

		db = MySQLdb.connect(host='localhost', port=3306, user='jedgar', passwd='abuelitos',
					db='jedgar_workfriends')
		cursor = db.cursor()

		cursor.execute("SELECT user_id FROM user")
		idList = cursor.fetchall()


		cursor.execute("SELECT user_id FROM match_triad WHERE week_number=\"{0}\"".format(self.timeStamp))
		idMatchedList = cursor.fetchall()

		prevPoolMatch = []
		for ill, in idMatchedList:
			prevPoolMatch.append(ill)

		for uid, in idList:
			if uid not in prevPoolMatch:
				self.pool.append(uid)

		for i in range(len(self.pool)):
			j = random.randint(0, len(self.pool)-1)
			k = random.randint(0, len(self.pool)-1)
			temp = self.pool[j]
			self.pool[j] = self.pool[k]
			self.pool[k] = temp

	def getTimeStamp(self):
		year, week, day = date.today().isocalendar()
		return str(year) + "." + str(week)


	def length(self):
		return len(self.pool)
		
	def pull(self):
		if (self.pool[0]):
			return self.pool.pop(0)

		return -1

	def printPool(self):
		for uid in self.pool:
			print uid
