#! /usr/local/bin/python

import MySQLdb
from Pool import Pool
from Triad import Triad


host = 'localhost'
port = 3306
username = 'jedgar'
passwd = 'abuelitos'
db = 'jedgar_workfriends'
match_triad_table = 'match_triad'
user_table = 'user'

db = MySQLdb.connect(host=host, port=port, user=username, passwd=passwd,
					db=db)
cursor = db.cursor()


class Grouper:

	triad_list = []
	week_number = ""
	matchesAcceptance = 0; #only add to a Triad if the user was matched with the people in it this number of times
	triad_list_size = 0

	def __init__(self):
		self.triad_list = []

## return history of the user
	def user_history(self, user):
		history = []
		query = 'SELECT match_1, match_2 FROM %s WHERE user_id = %d' % (match_triad_table, user)
		cursor.execute(query)
		for match in cursor.fetchall():
			(match_1, match_2) = match
			history.append(match_1)
			history.append(match_2)

		return history

## return # of times user is part of u2's history
	def check_user_history(self, u, u2):
		if u2:
			return self.user_history(u2).count(u)
		return 0

## main
	def group(self):
		pool = Pool()
		self.week_number = pool.getTimeStamp()
		self.maxTriad = pool.length()/3
		for i in range(pool.length()):
			user = pool.pull()
			t = self.checkTriads(user)
			if t == -1:
				self.createNewTriad(user)
				#print "Triad_list: %s" % self.triad_list[0].p1
			else:
				self.insertTriad(t, user)
				#print "Triad_list: %s" % self.triad_list[0].p1
		cursor.close()


	def createNewTriad(self, user):
		if self.maxTriad > 0:
			if self.triad_list_size < self.maxTriad:
				self.triad_list.append(Triad(user, None, None))
				self.triad_list_size += 1
				return True
			else:
				self.matchesAcceptance += 1
				i = self.checkTriads(user)
				if i != -1:
					self.insertTriad(i, user)
					return True
				else:
					return self.createNewTriad(user)

## return the index of the first triad user can be inserted to
## return -1 if none of the triads can take user
	def checkTriads(self, user):
		for i in range(len(self.triad_list)):
			user_1 = self.triad_list[i].p1
			user_2 = self.triad_list[i].p2
			if (self.check_user_history(user, user_1) <= self.matchesAcceptance) and (self.check_user_history(user, user_2) <= self.matchesAcceptance):
				return i
		return -1

	def insertToDB(self, u1, u2, u3):
		# need to insert to db
		query = 'INSERT INTO %s (user_id, match_1, match_2, week_number) VALUES (%d, %d, %d, %s)' % (match_triad_table, u1, u2, u3, self.week_number)
		cursor.execute(query)


	def returnTriad(self, t):
		return (self.triad_list[t].p1, self.triad_list[t].p2, self.triad_list[t].p3)

	def insertTriad(self, ind, user):
		self.triad_list[ind].addPerson(user)
		if self.triad_list[ind].isFull():
			# commit to db
			user_1 = self.triad_list[ind].p1
			user_2 = self.triad_list[ind].p2
			user_3 = self.triad_list[ind].p3
			self.insertToDB(user_1, user_2, user_3)
			self.insertToDB(user_2, user_1, user_3)
			self.insertToDB(user_3, user_1, user_2)
			self.triad_list.remove(self.triad_list[ind])


test = Grouper()
test.group()
#test.insertTriad(4, 5, 6)
#test.insertTriad(7, 8, 9)
#print test.returnTriad(0)



